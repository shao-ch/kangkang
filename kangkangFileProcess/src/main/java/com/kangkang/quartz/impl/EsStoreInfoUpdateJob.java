package com.kangkang.quartz.impl;

import com.alibaba.fastjson.JSONObject;
import com.kangkang.ESVO.ESStoreVO;
import com.kangkang.file.dao.EsInfoDao;
import com.kangkang.file.dao.esdao.ESStoreVORepository;
import com.kangkang.quartz.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: EsStoreInfoUpdateJob  ,将数据库的数据更新到es中
 * @Author: shaochunhai
 * @Date: 2021/12/22 3:08 下午
 * @Description: TODO
 */
@Component
@Slf4j
public class EsStoreInfoUpdateJob implements QuartzJob {
    @Autowired
    private EsInfoDao esInfoDao;

    @Autowired
    private ElasticsearchRestTemplate template;

    @Autowired
    private ESStoreVORepository repository;


    @Override
    public void excute() {
        try {
            log.info("=====数据库的数据更新到es开始执行====");
            List<ESStoreVO> esStoreVOS = esInfoDao.queryAllStore();

            //首先判断是否有索引
            boolean exists = template.indexExists(ESStoreVO.class);
            //如果不存在就创建索引
            if (!exists){
                boolean b = template.createIndex(ESStoreVO.class);
                if (b){
                    log.info("=======创建kangkang_store_info索引成功========");
                    //如果创建成功就要创建映射
                    template.putMapping(ESStoreVO.class);
                }else {
                    log.info("=======创建kangkang_store_info索引失败========");
                }
            }
            for (ESStoreVO esStoreVO : esStoreVOS) {
                log.info("=======开始将数据插入es========："+JSONObject.toJSONString(esStoreVO));

                repository.save(esStoreVO);

                //更新状态
                esInfoDao.updateEsVersion(esStoreVO.getSkuId(),"1");
//                //通过id查询是否已经插入，已经插入就更新
//                ESStoreVO o = template.queryForObject(GetQuery.getById(Long.toString(esStoreVO.getSkuId())), ESStoreVO.class
//                );
//                if (o!=null){
//
//
//                }
//                //新增数据
//                IndexQueryBuilder builder = new IndexQueryBuilder();
//                IndexQuery indexQuery = builder.withId(Long.toString(esStoreVO.getSkuId()))
//                        .withObject(esStoreVO).build();
//                template.index(indexQuery);
                //更新状态

                log.info("数据更新成功："+JSONObject.toJSONString(esStoreVO));



            }
        } catch (Exception e) {
            e.printStackTrace();
            //更新状态
            log.error("将数据库的数据更新到es中,异常信息为：",e);
        }
    }
}
