package com.kangkang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kangkang.store.entity.TbOrder;
import com.kangkang.store.viewObject.OrderVO;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    Map<String, Object> queryOrder(OrderVO order);


    @LoadBalanced
    @PostMapping(value = "order/createOrder")
    TbOrder createOrder(OrderVO order);

    /**
     * 查询全部订单列表
     * @param order
     * @return
     */
    @LoadBalanced
    @PostMapping(value = "order/queryOrderList")
    IPage<Map<String, Object>> queryOrderList(OrderVO order);
}
