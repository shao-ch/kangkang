package com.kangkang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kangkang.store.entity.TbOrder;
import com.kangkang.store.viewObject.OrderVO;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @InterfaceName: OrderService  订单服务
 * @Author: shaochunhai
 * @Date: 2021/8/27 9:18 上午
 * @Description: TODO
 */

public interface OrderService {

    /**
     * 查询订单初始化信息
     * @param order
     * @return
     */
    Map<String, Object> queryOrder(OrderVO order);

    /**
     * 生成订单
     * @param order
     */
    TbOrder createOrder(OrderVO order);

    /**
     * 查询全部订单列表
     * @param order
     * @return
     */
    IPage<Map<String, Object>> queryOrderList(OrderVO order);
}
