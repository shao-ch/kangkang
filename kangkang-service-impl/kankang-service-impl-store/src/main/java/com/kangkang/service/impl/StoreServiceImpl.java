package com.kangkang.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.ESVO.ESStoreVO;
import com.kangkang.dao.TbAfterSaleDao;
import com.kangkang.dao.TbCommentDao;
import com.kangkang.dao.TbSkuDao;
import com.kangkang.dao.TbStoreDao;
import com.kangkang.manage.entity.TbComment;
import com.kangkang.manage.viewObject.TbCommentVO;
import com.kangkang.service.StoreService;
import com.kangkang.store.entity.TbAfterSale;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.entity.TbStock;
import com.kangkang.store.entity.TbStore;
import com.kangkang.store.viewObject.TbStoreVO;
import com.kangkang.tools.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

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


    @Override
    @Transactional(readOnly = true)
    public Page<TbStoreVO> queryStoreInfo(PageUtils pageUtils) {
        //首先判断索引存不存在
        boolean b = template.indexExists(ESStoreVO.class);
        if (!b){
            log.info("该ESStoreVO的类所在的索引不存在");
            return null;
        }

        //设置查询
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //设置分页范围
        builder.withPageable(PageRequest.of(pageUtils.getPageIndex()-1,pageUtils.getPageSize()));
        //匹配全部数据,这里通过这个可以对数据进行筛选过滤
        BoolQueryBuilder must = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery());
        builder.withQuery(must);
        AggregatedPage<ESStoreVO> esStoreVOS = template.queryForPage(builder.build(), ESStoreVO.class);


        //获取首先的分页数据
        Page<TbStoreVO> page = new Page<>(pageUtils.getPageIndex(),pageUtils.getPageSize());

        Page<TbStoreVO> TbStoreVO = tbStoreDao.selectStoreInfo(page);

        return TbStoreVO;
    }


    /**
     * 通过id查询商品详细信息
     * @param id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)   //这里要设置只读
    public TbStoreVO getStoreDetail(Long id) {

        TbStoreVO result = new TbStoreVO();
        QueryWrapper<TbStore> wrapper = new QueryWrapper<>();
        //第一步查询商品详情实体
        wrapper.eq("id",id);
        TbStore tbStore = tbStoreDao.selectOne(wrapper);
        Object specArgument = tbStore.getSpecArgument();
        List<String> parse = (List<String>) JSONObject.parse(specArgument.toString());
        log.info("转化的数据为："+JSONObject.toJSONString(parse));
        tbStore.setSpecArgument(JSONObject.toJSON(parse));
        //bean的转化
        BeanUtils.copyProperties(tbStore,result);
        //获取cid
        Long cid = tbStore.getTbCategoryId();
        //通过cid查询详情信息
        List<TbAfterSale> tbAfterSales = tbAfterSaleDao.selectListByCid(cid);
        //将售后详情数据放入结果中
        result.setTbAfterSales(tbAfterSales);
        return result;
    }


    /**
     * 立即购买 获取商品实体数据
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
     * @param tbSkuId
     * @return
     */
    @Override
    public TbStock getStock(Long tbSkuId) {
        return tbSkuDao.getStock(tbSkuId);
    }

    /**
     * 通过主键获取sku商品主体信息
     * @param skuIds
     */
    @Override
    @Transactional(readOnly = true)
    public List<TbSku> getSkuById(List<Long> skuIds) {
        return tbSkuDao.selectBatchIds(skuIds);
    }

    /**
     * 新增评论
     * @param tbCommentVO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addComment(TbCommentVO tbCommentVO) {
        TbComment tbComment = new TbComment();
        BeanUtils.copyProperties(tbCommentVO,tbComment);
        tbCommentDao.insert(tbComment);
    }


    /**
     * 查询累计评论
     * @param tbCommentVO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TbComment> queryCommentInfo(TbCommentVO tbCommentVO) {

        Page<TbComment> page = new Page<>(tbCommentVO.getPageIndex(), tbCommentVO.getPageSize());
        //0-代表查询商品评论，1-代表查询回复评论
        if (StringUtils.equals("0", tbCommentVO.getFlag()))
            return tbCommentDao.queryCommentInfoByStoreId(page, tbCommentVO.getTbStoreId());
        return tbCommentDao.queryReplyCommentInfo(page, tbCommentVO.getId(), tbCommentVO.getTbStoreId());
    }


    /**
     * 点赞数
     *
     * @param flag 0-带表减1，1-代表加1
     * @param id  评论表的id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class,timeout = 1000)
    public Integer clickZAN(String flag, Long id) {
        if ("0".equals(flag))
         tbCommentDao.reduceZANById(id);
        tbCommentDao.addZANById (id);

        //然后查询结果
        return tbCommentDao.selectZANById(id);
    }
}
