package com.kangkang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.store.viewObject.OrderPageVO;
import com.kangkang.store.viewObject.OrderVO;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.Map;

/**
 * @InterfaceName: OrderFeignService
 * @Author: shaochunhai
 * @Date: 2021/8/26 2:56 下午
 * @Description: TODO
 */
public interface OrderFeignService {

    /**
     * 查询订单初始化信息
     * @param order
     * @return
     */
    @LoadBalanced
    @GetMapping(value = "order/queryOrder")
    Map<String, Object> queryOrder(@RequestBody OrderVO order);


    @LoadBalanced
    @PostMapping(value = "order/createOrder")
    Map<String,Object> createOrder(@RequestBody OrderVO order);

    /**
     * 查询全部订单列表
     * @param order
     * @return
     */
    @LoadBalanced
    @PostMapping(value = "order/queryOrderList")
    Page<Map<String, Object>> queryOrderList(@RequestBody OrderPageVO order);
}
