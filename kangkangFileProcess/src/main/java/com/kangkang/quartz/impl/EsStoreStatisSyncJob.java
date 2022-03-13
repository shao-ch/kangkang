package com.kangkang.quartz.impl;

import com.alibaba.fastjson.JSONObject;
import com.kangkang.ESVO.ESStoreStatisVO;
import com.kangkang.file.dao.EsInfoDao;
import com.kangkang.file.dao.esdao.EsStoreStatisRepository;
import com.kangkang.quartz.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: EsStoreStatisSyncJob  更新statis的数据统计基础信息的修改
 * @Author: shaochunhai
 * @Date: 2022/1/3 4:14 下午
 * @Description: TODO
 */
@Component
@Slf4j
public class EsStoreStatisSyncJob implements QuartzJob {

    @Autowired
    private EsInfoDao esInfoDao;

    @Autowired
    private ElasticsearchRestTemplate template;

    @Autowired
    private EsStoreStatisRepository repository;

    @Override
    public void excute() {
        try {
            log.info("=====表tb_store的统计数据更新到es开始执行====");
            List<ESStoreStatisVO> esStoreStatisVOS = esInfoDao.queryAllStoreInfo();

            if (esStoreStatisVOS.size()<=0){
                log.info("tb_store中没有所需要更新，数据条数为：["+esStoreStatisVOS.size()+"]");
                return;
            }

            //首先判断是否有索引
            boolean exists = template.indexExists(ESStoreStatisVO.class);
            //如果不存在就创建索引
            if (!exists) {
                boolean b = template.createIndex(ESStoreStatisVO.class);
                if (b) {
                    log.info("=======创建kangkang_store_statis_info索引成功========");
                    //如果创建成功就要创建映射
                    template.putMapping(ESStoreStatisVO.class);
                } else {
                    log.info("=======创建kangkang_store_statis_info索引失败========");
                }
            }
            for (ESStoreStatisVO storeStatisVO : esStoreStatisVOS) {
                log.info("=======开始将数据插入es========：" + JSONObject.toJSONString(storeStatisVO));

                repository.save(storeStatisVO);

                //更新状态
                esInfoDao.updateEsFlag(storeStatisVO.getId(), "1");
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

                log.info("数据更新成功：" + JSONObject.toJSONString(storeStatisVO));


            }
        } catch (Exception e) {
            e.printStackTrace();
            //更新状态
            log.error("将数据库的数据更新到es中,异常信息为：", e);
        }
    }
}

