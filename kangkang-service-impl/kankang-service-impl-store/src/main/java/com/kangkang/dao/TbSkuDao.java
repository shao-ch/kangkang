package com.kangkang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.entity.TbStock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: TbSkuDao   商品实体表
 * @Author: shaochunhai
 * @Date: 2021/8/25 1:20 下午
 * @Description: TODO
 */
@Repository
public interface TbSkuDao extends BaseMapper<TbSku> {


    TbStock getStock(@Param("tbSkuId") Long tbSkuId);
}
