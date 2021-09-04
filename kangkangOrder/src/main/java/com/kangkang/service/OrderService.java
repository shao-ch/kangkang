package com.kangkang.service;

import com.kangkang.store.viewObject.OrderVO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @InterfaceName: OrderService  订单服务
 * @Author: shaochunhai
 * @Date: 2021/8/26 2:19 下午
 * @Description: TODO
 */
@FeignClient("kangkang-order-service")
public interface OrderService extends OrderFeignService{

}
