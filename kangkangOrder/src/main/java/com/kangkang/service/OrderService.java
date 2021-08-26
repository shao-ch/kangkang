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
@FeignClient("kangkang-store-order")
public interface OrderService extends OrderFeignService{

    /**
     * 查询订单初始化信息
     * @param order
     * @return
     */
    @Override
    default Map<String, Object> queryOrder(OrderVO order) {
        return null;
    }
}
