package com.kangkang.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kangkang.service.OrderFeignService;
import com.kangkang.service.OrderService;
import com.kangkang.store.entity.TbOrder;
import com.kangkang.store.viewObject.OrderVO;
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
    public TbOrder createOrder(OrderVO order) {
       return orderService.createOrder(order);
    }

    /**
     * 查询全部订单列表
     * @param order
     * @return
     */
    @Override
    public IPage<Map<String, Object>> queryOrderList(OrderVO order) {
        return orderService.queryOrderList(order);
    }
}
