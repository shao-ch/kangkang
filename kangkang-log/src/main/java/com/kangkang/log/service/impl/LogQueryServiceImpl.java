package com.kangkang.log.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.kangkang.log.service.LogQueryService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @ClassName: LogQueryServiceImpl
 * @Author: shaochunhai
 * @Date: 2021/12/13 5:47 下午
 * @Description: TODO
 */
@Slf4j
@Service
public class LogQueryServiceImpl implements LogQueryService {
    //这个是通过client去查询你所需要的数据，ElasticsearchRestTemplate这个是通过实例类去映射数据，也就是通过@document等注解注入实体类的方式
    @Autowired
    private RestHighLevelClient client;

    @Override
    public void logQuery() {

        List<String> result =new ArrayList<>();
        try {
            //获取请求查询的类
            SearchRequest searchRequest = new SearchRequest();
            //查询哪个索引的内容
            searchRequest.indices("kangkang-file-process");
            //设置索引类型
            searchRequest.types("_doc");
            //构建查询
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            //设置匹配规则
            boolQueryBuilder.filter(QueryBuilders.matchAllQuery());
            //查询的数据范围
            sourceBuilder.query(boolQueryBuilder).from(0).size(10000);
            //构建请求
            searchRequest.source(sourceBuilder);
            //查询，然后获取返回数据
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHits hits = response.getHits();
            Iterator<SearchHit> iterator = hits.iterator();
            while (iterator.hasNext()){
                SearchHit next = iterator.next();
                Map<String, Object> sourceAsMap = next.getSourceAsMap();
                result.add(sourceAsMap.get("message").toString());
                System.out.println(sourceAsMap.get("message"));
            }
            String o = JSONObject.toJSONString(hits);
            HashMap<String, Object> hashMap = JSONObject.parseObject(o, new HashMap<String, Object>().getClass());


        } catch (IOException e) {
            e.printStackTrace();
        }

//        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//
//        if ("0".equals("0")){
//            //模糊查询
////            String context = vo.getContext();
////            //获取模糊查询的参数
////            String[] split = context.split("-");
//            //遍历key，不同的key获取不同的值
//            //构建查询
//            queryBuilder.withIndices("kangkang-file-process");
//            queryBuilder.withTypes("doc");
//            queryBuilder.withSearchType(SearchType);
//            BoolQueryBuilder builder = QueryBuilders.boolQuery();
//            builder.must(QueryBuilders.matchQuery("message","text"));
////
////
//
////            queryBuilder.withQuery(builder);
//            List<? extends HashMap> list=elasticsearch.queryForList(queryBuilder.build(), new HashMap<String,Object>().getClass());
//
//            System.out.println("maps = " + list);
//
//        }

    }





}
