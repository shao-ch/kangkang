package com.kangkang.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @InterfaceName: OrderService  订单服务
 * @Author: shaochunhai
 * @Date: 2021/8/26 2:19 下午
 * @Description: TODO
 */
@FeignClient("kangkang-order-service")
public interface OrderService extends OrderFeignService{

}
