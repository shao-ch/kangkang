package com.kangkang.file.dao;

import com.kangkang.ESVO.ESStoreVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName: EsInfoDao  专门为es更新数据创建的类型
 * @Author: shaochunhai
 * @Date: 2021/12/22 3:31 下午
 * @Description: TODO
 */
@Repository
public interface EsInfoDao {

    /**
     * 查询所有状态为未更新的数据，也就是es_version为0的数据
     * @return
     */
    List<ESStoreVO> queryAllStore();

    /**
     * 将更新到es的数据的版本更新为1，也就是置为最新的状态
     */
    void updateEsVersion(@Param("skuId") Long skuId,@Param("esVersion") String esVersion);
}
