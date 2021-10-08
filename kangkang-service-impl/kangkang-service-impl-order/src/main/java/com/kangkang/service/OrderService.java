package com.kangkang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.store.entity.TbOrder;
import com.kangkang.store.entity.TbShoppingCar;
import com.kangkang.store.viewObject.OrderPageVO;
import com.kangkang.store.viewObject.OrderVO;
import com.kangkang.store.viewObject.OrderView;
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
    Map<String,Object> createOrder(OrderVO order);

    /**
     * 查询全部订单列表
     * @param order
     * @return
     */
    Page<Map<String, Object>> queryOrderList(OrderPageVO order);

    /**
     * 查询代付款订单
     * @param orderPageVO
     * @return
     */
    Page<OrderView> queryPayingOrder(OrderPageVO orderPageVO);

    /**
     * 添加购物车
     * @param shoppingCar
     */
    void addShoppingCar(TbShoppingCar shoppingCar);
}
