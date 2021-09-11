package com.kangkang.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.service.OrderFeignService;
import com.kangkang.service.OrderService;
import com.kangkang.store.entity.TbOrder;
import com.kangkang.store.viewObject.OrderPageVO;
import com.kangkang.store.viewObject.OrderVO;
import com.kangkang.store.viewObject.OrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName: OrderFeignController
 * @Author: shaochunhai
 * @Date: 2021/8/27 9:16 上午
 * @Description: TODO
 */
@RestController
public class OrderFeignController implements OrderFeignService {

    @Autowired
    private OrderService orderService;

    /**
     * 查询订单初始化信息
     *
     * @param order
     * @return
     */
    @Override
    public Map<String, Object> queryOrder(OrderVO order) {
        return orderService.queryOrder(order);
    }

    /**
     * 生成订单
     *
     * @param order
     */
    @Override
    public Map<String,Object> createOrder(OrderVO order) {
       return orderService.createOrder(order);
    }

    /**
     * 查询全部订单列表
     * @param order
     * @return
     */
    @Override
    public Page<Map<String, Object>> queryOrderList(OrderPageVO order) {
        return orderService.queryOrderList(order);
    }


    /**
     * 查询代付款订单
     * @param orderPageVO
     * @return
     */
    @Override
    public Page<OrderView> queryPayingOrder(OrderPageVO orderPageVO) {
        return orderService.queryPayingOrder(orderPageVO);
    }
}
