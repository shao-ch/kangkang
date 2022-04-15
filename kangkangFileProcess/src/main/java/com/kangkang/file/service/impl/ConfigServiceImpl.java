package com.kangkang.file.service.impl;

import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.kangkang.ESVO.ESStoreVO;
import com.kangkang.file.dao.TbConfigInfoDao;
import com.kangkang.file.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ConfigServiceImpl
 * @Author: shaochunhai
 * @Date: 2021/9/22 10:49 上午
 * @Description: TODO
 */
@Slf4j
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private TbConfigInfoDao tbConfigInfoDao;

    @Autowired
    private ElasticsearchRestTemplate template;

    @Value("${file.hostname}")
    private String hostname;
    @Value("${file.username}")
    private String username;
    @Value("${file.password}")
    private String password;
    @Value("${file.commentPath}")
    private String commentPath;

    /**
     * 查询所有配置类信息
     *
     * @return
     */
    @Override
    public HashMap<String, String> initConfigData() {

       final HashMap<String, String> result = tbConfigInfoDao.queryAllConfig();
        return result;
    }


    /**
     * 文件上传
     *
     * @param files
     * @return
     */
    @Override
    public String commentFileUpload(List<MultipartFile> files) {
        FTPClient client = null;
        try {
            //这里要去连接三次，如果三次还是不能连接成功就返回数据
            for (int i = 0; i < 3; i++) {
                //创建ftp实例
                client = new FTPClient();
                //设置连接响应时间
                client.setConnectTimeout(10000);
                //连接
                client.connect(hostname, 22);
                //登陆
                boolean login = client.login(username, password);
                if (!login) {
                    log.info("评论上传文件，ftp登陆超时.......");
                    if (client.isConnected()) {
                        //这里要关闭连接
                        client.disconnect();
                        //这里重新置为null
                        client = null;
                        continue;
                    }
                }
                //如果登陆成功就跳出循环
                // 设置字符编码
                client.setControlEncoding("UTF-8");
                //基本路径，一定存在
                String basePath = "/";
                String[] pathArray = commentPath.split("/");
                for (String path : pathArray) {
                    basePath += path + "/";
                    //3.指定目录 返回布尔类型 true表示该目录存在
                    boolean dirExsists = client.changeWorkingDirectory(basePath);
                    //4.如果指定的目录不存在，则创建目录
                    if (!dirExsists) {
                        //此方式，每次，只能创建一级目录
                        boolean flag = client.makeDirectory(basePath);
                        if (flag) {
                            log.info("====" + basePath + "====目录创建成功！");
                        }
                    }
                }

                //重新指定上传文件的路径
                client.changeWorkingDirectory(basePath);
                //5.设置上传文件的方式
                client.setFileType(FTP.BINARY_FILE_TYPE);
                break;
            }

            if (client == null) {
                return "ftp连接失败";
            }
            for (MultipartFile file : files) {

                //获取文件名
                String name = file.getName();
                InputStream inputStream = file.getInputStream();
                //重新指定上传
                boolean b = client.storeFile(name, inputStream);

                if (!b) {
                    return "图片上传失败";
                } else {
                    log.info("【" + name + "】" + "===文件上传成功！");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void queryTest() {

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
        ArrayList<Integer> idContext = new ArrayList<>();
        ArrayList<ESStoreVO> result = new ArrayList<>();
        String like="手机";
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (null!=like){
            boolQuery.must(QueryBuilders.termQuery("skuTitle",like));
        }

        List<Aggregation> aggregations = getAggregations(boolQuery);
        //获取聚合数据
        for (Aggregation aggregation : aggregations) {
            //聚合转换成解析对象
            ParsedLongTerms agg= (ParsedLongTerms) aggregation;
            //获取你的分组数据
            List<? extends Terms.Bucket> buckets = agg.getBuckets();
            //根据商品id分组取id最大的，后期加入访问量数据
            if (!buckets.isEmpty()){
                for (Terms.Bucket aaa : buckets) {
                    Aggregations ccc = aaa.getAggregations();
                    List<Aggregation> ddd = ccc.asList();
                    if (!ddd.isEmpty()){
                        for (Aggregation eee : ddd) {
                            ParsedTopHits fff=(ParsedTopHits)eee;
                            SearchHits hits = fff.getHits();
                            SearchHit[] hits1 = hits.getHits();
                            if (hits1!=null&&hits1.length>0){
                                for (SearchHit sea : hits1) {
                                    String id = sea.getId();
                                    idContext.add(Integer.valueOf(id));
                                    Map<String, Object> sourceAsMap = sea.getSourceAsMap();
                                    ESStoreVO esStoreVO = BeanUtils.mapToBean(sourceAsMap, ESStoreVO.class);
                                    result.add(esStoreVO);
                                }
                            }
                        }
                    }

                }
            }

        }
        //二次补录信息凑够首页的50条消息，目前设置为2条
        if (idContext.size()<2){
            BoolQueryBuilder builder1 = QueryBuilders.boolQuery();
            builder1.mustNot(QueryBuilders.termsQuery("_id",result));
            List<Aggregation> aggregationList = getAggregations(builder1);

            for (Aggregation aggregation : aggregationList) {
                //聚合转换成解析对象
                ParsedLongTerms ccc= (ParsedLongTerms) aggregation;
                //获取你的分组数据
                List<? extends Terms.Bucket> f = ccc.getBuckets();
                //根据商品id分组取id最大的，后期加入访问量数据
                for (Terms.Bucket aaa : f) {
                    Aggregations g = aaa.getAggregations();
                    List<Aggregation> ddd = g.asList();
                    for (Aggregation eee : ddd) {
                        ParsedTopHits fff=(ParsedTopHits)eee;
                        SearchHits hits = fff.getHits();
                        SearchHit[] hits1 = hits.getHits();
                        for (SearchHit sea : hits1) {
                            Map<String, Object> sourceAsMap = sea.getSourceAsMap();
                            ESStoreVO esStoreVO = BeanUtils.mapToBean(sourceAsMap, ESStoreVO.class);
                            result.add(esStoreVO);

                        }
                    }

                }
            }

        }

        System.out.println("result = " + result);



    }

    private List<Aggregation> getAggregations(QueryBuilder builder) {
        //构建聚合
        TermsAggregationBuilder termsAgg = AggregationBuilders.terms("aaa").field("storeId");  //这里按照某个字段做聚合
        TopHitsAggregationBuilder size = AggregationBuilders.topHits("top_score").sort("skuId", SortOrder.DESC)
                .from(0).size(1);
        termsAgg.subAggregation(size);

        //这里要做的就是分组排序取第一个

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder.withIndices("kangkang_store_info");
        queryBuilder.withTypes("doc");
        queryBuilder.withQuery(builder);
        queryBuilder.addAggregation(termsAgg);
        AggregatedPage<ESStoreVO> esStoreVOS = template.queryForPage(queryBuilder.build(), ESStoreVO.class);
        //下面是获取buckdet桶的数据
        Aggregations aggs = esStoreVOS.getAggregations();
        //有可能有多条数据
        List<Aggregation> aggregations = aggs.asList();
        return aggregations;
    }
}
