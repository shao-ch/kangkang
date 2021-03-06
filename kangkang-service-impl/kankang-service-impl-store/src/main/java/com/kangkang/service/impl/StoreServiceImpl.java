package com.kangkang.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.ESVO.ESStoreVO;
import com.kangkang.dao.TbAfterSaleDao;
import com.kangkang.dao.TbCommentDao;
import com.kangkang.dao.TbSkuDao;
import com.kangkang.dao.TbStoreDao;
import com.kangkang.enumInfo.RocketInfo;
import com.kangkang.manage.entity.TbComment;
import com.kangkang.manage.dtoObject.TbCommentDTO;
import com.kangkang.service.StoreService;
import com.kangkang.store.entity.TbAfterSale;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.entity.TbStock;
import com.kangkang.store.entity.TbStore;
import com.kangkang.store.dtoObject.StoreSearchDTO;
import com.kangkang.store.dtoObject.TbStoreDTO;
import com.kangkang.tools.KangkangBeanUtils;
import com.kangkang.untils.MqUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.ParsedTopHits;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsAggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: StoreServiceImpl
 * @Author: shaochunhai
 * @Date: 2021/8/12 9:56 上午
 * @Description: TODO
 */
@Slf4j
@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private TbStoreDao tbStoreDao;

    @Autowired
    private TbSkuDao tbSkuDao;

    @Autowired
    private TbAfterSaleDao tbAfterSaleDao;

    @Autowired
    private TbCommentDao tbCommentDao;

    @Autowired
    private ElasticsearchRestTemplate template;

    @Autowired
    private DefaultMQProducer producer;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    @Transactional(readOnly = true)
    public List<TbStoreDTO> queryStoreInfo(StoreSearchDTO storeSearchDTO) {
        /**
         * 这里是高亮处理
         * new HighlightBuilder.Field("name").preTags(preTag).postTags(postTag),
         *                         new HighlightBuilder.Field("description").preTags(preTag).postTags(postTag)).build()
         */
        /**构建查询,
         * multiMatchQuery  多字段查询
         * matchQuery 单个字段匹配
         * match会对你所查询的条件进行分词，然后搜索，term不会对你查询的内容进行过滤，我的需求是不过滤，直接使用完全匹配(term)
         */
        BoolQueryBuilder boolQuery;
        ArrayList<Long> idContext = new ArrayList<>();
        ArrayList<TbStoreDTO> result = new ArrayList<>();
        List<String> likeContext = null;

        //查询此用户的喜好
        Object o = redisTemplate.opsForValue().get(storeSearchDTO.getLoginName());

        if (storeSearchDTO.getSearchInfo() != null) {
            //这里是一个算法，就是记录用户最近查询的十条喜好
            if (o == null) {
                //这里面代表是该用户没有任何喜好
                likeContext = new ArrayList<>();
                likeContext.add(storeSearchDTO.getSearchInfo());
                String s = JSONObject.toJSONString(likeContext);
                //使用原子操作，防止存在同一个用户的多条喜好记录
                redisTemplate.opsForValue().setIfAbsent(storeSearchDTO.getLoginName(), s);

            } else {  //说明用户有喜好

                likeContext = JSONObject.parseArray(o.toString(), String.class);
                //如果list中不包含并且list转化的字符串中也不包含该词那就存入喜好容器
                if ((!likeContext.contains(storeSearchDTO.getSearchInfo())) && (!o.toString().contains(storeSearchDTO.getSearchInfo()))) {
                    likeContext.add(storeSearchDTO.getSearchInfo());
                    String s = JSONObject.toJSONString(likeContext);
                    //使用原子操作，防止存在同一个用户的多条喜好记录
                    redisTemplate.opsForValue().getAndSet(storeSearchDTO.getLoginName(), s);
                }
            }

            //搜索框的查询消息
            boolQuery = QueryBuilders.boolQuery();

            boolQuery.should(QueryBuilders.matchQuery("skuTitle", storeSearchDTO.getSearchInfo()));

        } else {
            //查询喜好
            if (o == null) {
                likeContext = new ArrayList<>();
            }else {
                likeContext = JSONObject.parseArray(o.toString(), String.class);
            }

            boolQuery = QueryBuilders.boolQuery();

            //查询喜好数据
            for (String like : likeContext) {
                boolQuery.should(QueryBuilders.matchQuery("skuTitle", like));
            }
//            boolQuery.should(QueryBuilders("skuTitle", likeContext));   这里要用多条件查询，也就是es中的or，下面也是一样

        }


        List<Aggregation> aggregations = getAggregations(boolQuery,1);
        //获取聚合数据
        for (Aggregation aggregation : aggregations) {
            //聚合转换成解析对象
            ParsedLongTerms agg = (ParsedLongTerms) aggregation;
            //获取你的分组数据
            List<? extends Terms.Bucket> buckets = agg.getBuckets();
            //根据商品id分组取id最大的，后期加入访问量数据
            if (!buckets.isEmpty()) {
                for (Terms.Bucket aaa : buckets) {
                    Aggregations ccc = aaa.getAggregations();
                    List<Aggregation> ddd = ccc.asList();
                    if (!ddd.isEmpty()) {
                        for (Aggregation eee : ddd) {
                            ParsedTopHits fff = (ParsedTopHits) eee;
                            SearchHits hits = fff.getHits();
                            SearchHit[] hits1 = hits.getHits();
                            if (hits1 != null && hits1.length > 0) {
                                for (SearchHit sea : hits1) {
                                    String id = sea.getId();
                                    idContext.add(Long.valueOf(id));
                                    Map<String, Object> sourceAsMap = sea.getSourceAsMap();
                                    ESStoreVO esStoreVO = KangkangBeanUtils.mapToBean(sourceAsMap, ESStoreVO.class);
                                    //数据转化
                                    TbStoreDTO tbStoreDTO = esStoreToTbStoreVO(esStoreVO);
                                    result.add(tbStoreDTO);
                                }
                            }
                        }
                    }
                }
            }

        }
        //二次补录信息凑够首页的50条消息，目前设置为2条
        if (idContext.size() < 1) {
            BoolQueryBuilder builder1 = QueryBuilders.boolQuery();
            //这里是说明不包含上面查询的数据
            builder1.mustNot(QueryBuilders.termsQuery("_id", result));
            //这里设置首页数据
            List<Aggregation> aggregationList = getAggregations(builder1,1);

            for (Aggregation aggregation : aggregationList) {
                //聚合转换成解析对象
                ParsedLongTerms ccc = (ParsedLongTerms) aggregation;
                //获取你的分组数据
                List<? extends Terms.Bucket> f = ccc.getBuckets();
                //根据商品id分组取id最大的，后期加入访问量数据
                for (Terms.Bucket aaa : f) {
                    Aggregations g = aaa.getAggregations();
                    List<Aggregation> ddd = g.asList();
                    for (Aggregation eee : ddd) {
                        ParsedTopHits fff = (ParsedTopHits) eee;
                        SearchHits hits = fff.getHits();
                        SearchHit[] hits1 = hits.getHits();
                        for (SearchHit sea : hits1) {
                            Map<String, Object> sourceAsMap = sea.getSourceAsMap();
                            ESStoreVO esStoreVO = KangkangBeanUtils.mapToBean(sourceAsMap, ESStoreVO.class);
                            TbStoreDTO tbStoreDTO = esStoreToTbStoreVO(esStoreVO);
                            result.add(tbStoreDTO);

                        }
                    }

                }
            }

        }
        //数据返回
        return result;
    }

    /**
     * 将es查询的数据转化成前台要的数据
     *
     * @param es
     * @return
     */
    private TbStoreDTO esStoreToTbStoreVO(ESStoreVO es) {
        TbStoreDTO tbStoreDTO = new TbStoreDTO();
        tbStoreDTO.setSkuId(es.getSkuId());
        tbStoreDTO.setPrice(es.getSkuPrice());
        tbStoreDTO.setTitle(es.getSkuTitle());
        tbStoreDTO.setId(es.getStoreId());
        tbStoreDTO.setImage(es.getStoreImage());
        tbStoreDTO.setSpecification(es.getStoreSpecification());
        tbStoreDTO.setSpecArgument(es.getStoreSpecArgument());
        return tbStoreDTO;
    }


    /**
     * 通过id查询商品详细信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)   //这里要设置只读
    public TbStoreDTO getStoreDetail(Long id) {

        TbStoreDTO result = new TbStoreDTO();
        QueryWrapper<TbStore> wrapper = new QueryWrapper<>();
        //第一步查询商品详情实体
        wrapper.eq("id", id);
        TbStore tbStore = tbStoreDao.selectOne(wrapper);
        Object specArgument = tbStore.getSpecArgument();
        List<String> parse = (List<String>) JSONObject.parse(specArgument.toString());
        log.info("转化的数据为：" + JSONObject.toJSONString(parse));
        tbStore.setSpecArgument(JSONObject.toJSON(parse));
        //bean的转化
        BeanUtils.copyProperties(tbStore, result);
        //获取cid
        Long cid = tbStore.getTbCategoryId();
        //通过cid查询详情信息
        List<TbAfterSale> tbAfterSales = tbAfterSaleDao.selectListByCid(cid);
        //将售后详情数据放入结果中
        result.setTbAfterSales(tbAfterSales);

        //这里做一次评分的处理，使用mq
        try {
            HashMap<String, Object> mqInfo = new HashMap<>();
            mqInfo.put(String.valueOf(tbStore.getId()), 1);
            MqUtils.send(producer, RocketInfo.SEND_STORE_SCORE_TOPIC, RocketInfo.SEND_STORE_SCORE_TAG, JSONObject.toJSONString(mqInfo));
        } catch (MQBrokerException e) {
            e.printStackTrace();
            log.error("更新商品评分异常：", e);
        } catch (RemotingException e) {
            e.printStackTrace();
            log.error("更新商品评分异常：", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("更新商品评分异常：", e);
        } catch (MQClientException e) {
            e.printStackTrace();
            log.error("更新商品评分异常：", e);
        }

        return result;
    }


    /**
     * 立即购买 获取商品实体数据
     *
     * @param skuId
     * @return
     */
    @Override
    public TbSku getSkuData(Long skuId) {
        TbSku tbSku = tbSkuDao.selectById(skuId);
        Object specParams = tbSku.getSpecParams();
        List<String> parse = (List<String>) JSONObject.parse(specParams.toString());
        tbSku.setSpecParams(JSONObject.toJSON(parse));
        return tbSku;
    }


    /**
     * 查询库存
     *
     * @param tbSkuId
     * @return
     */
    @Override
    public TbStock getStock(Long tbSkuId) {
        return tbSkuDao.getStock(tbSkuId);
    }

    /**
     * 通过主键获取sku商品主体信息
     *
     * @param skuIds
     */
    @Override
    @Transactional(readOnly = true)
    public List<TbSku> getSkuById(List<Long> skuIds) {
        return tbSkuDao.selectBatchIds(skuIds);
    }

    /**
     * 新增评论
     *
     * @param tbCommentDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addComment(TbCommentDTO tbCommentDTO) {
        TbComment tbComment = new TbComment();
        BeanUtils.copyProperties(tbCommentDTO, tbComment);
        tbCommentDao.insert(tbComment);
    }


    /**
     * 查询累计评论
     *
     * @param tbCommentDTO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TbComment> queryCommentInfo(TbCommentDTO tbCommentDTO) {

        Page<TbComment> page = new Page<>(tbCommentDTO.getPageIndex(), tbCommentDTO.getPageSize());
        //0-代表查询商品评论，1-代表查询回复评论
        if (StringUtils.equals("0", tbCommentDTO.getFlag()))
            return tbCommentDao.queryCommentInfoByStoreId(page, tbCommentDTO.getTbStoreId());
        return tbCommentDao.queryReplyCommentInfo(page, tbCommentDTO.getId(), tbCommentDTO.getTbStoreId());
    }


    /**
     * 点赞数
     *
     * @param flag 0-带表减1，1-代表加1
     * @param id   评论表的id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 1000)
    public Integer clickZAN(String flag, Long id) {
        if ("0".equals(flag))
            tbCommentDao.reduceZANById(id);
        tbCommentDao.addZANById(id);

        //然后查询结果
        return tbCommentDao.selectZANById(id);
    }


    private List<Aggregation> getAggregations(QueryBuilder builder,Integer dataSize) {
        //构建聚合
        TermsAggregationBuilder termsAgg = AggregationBuilders.terms("aaa").field("storeId").size(dataSize);  //这里按照某个字段做聚合
        TopHitsAggregationBuilder size = AggregationBuilders.topHits("top_score").sort("skuId", SortOrder.DESC)
                .from(0).size(1);
        termsAgg.subAggregation(size);


        //这里要做的就是分组排序取第一个

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder.withIndices("kangkang_store_info");
        queryBuilder.withTypes("doc");
        queryBuilder.withQuery(builder);
       //这里设置需要查询多少条数据
        queryBuilder.addAggregation(termsAgg);
        AggregatedPage<ESStoreVO> esStoreVOS = template.queryForPage(queryBuilder.build(), ESStoreVO.class);
        //下面是获取buckdet桶的数据
        Aggregations aggs = esStoreVOS.getAggregations();
        //有可能有多条数据
        List<Aggregation> aggregations = aggs.asList();
        return aggregations;
    }
}
