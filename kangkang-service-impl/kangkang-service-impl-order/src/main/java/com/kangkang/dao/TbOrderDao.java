package com.kangkang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.store.entity.TbOrder;
import com.kangkang.store.entity.TbSku;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @ClassName: TbOrderDao  订单表的持久层
 * @Author: shaochunhai
 * @Date: 2021/8/27 9:19 上午
 * @Description: TODO
 */
@Repository
public interface TbOrderDao extends BaseMapper<TbOrder> {

    /**
     * 查询所有订单
     * @param page
     * @param openId
     * @return
     */
    Page<Map<String, Object>> queryInfoByOpenId(Page<Map<String, Object>> page,@Param("openId") String openId);

    /**
     * 查询代付款的订单
     * @param page
     * @param openId
     * @return
     */
    Page<TbOrder> selectPayingOrder(Page<TbOrder> page, @Param("openId") String openId,
                                                @Param("orderStatus") String orderStatus);

    /**
     * 通过id查询sku信息
     * @param skuId
     * @return
     */
    TbSku selectSkuInfo(@Param("skuId")Long skuId);
}
