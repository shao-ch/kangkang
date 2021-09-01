package com.kangkang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.store.entity.TbStock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: TbStockDao  库存表
 * @Author: shaochunhai
 * @Date: 2021/8/30 4:31 下午
 * @Description: TODO
 */

@Repository
public interface TbStockDao extends BaseMapper<TbStock> {


    /**
     * 扣减库存的的数量 (查询的时候已经减去1了)
     * @param skuId
     * @return
     */
    TbStock queryStockById(@Param("SkuId") Long skuId);

    /**
     * 通过id更新数据
     * @param id
     */
    void updateStockById(@Param("skuId") Long skuId);
}
