package com.kangkang.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @InterfaceName: StoreService
 * @Author: shaochunhai
 * @Date: 2021/8/12 9:42 上午
 * @Description: TODO
 */
@FeignClient("kangkang-store-service")
public interface StoreService extends KangkangApiService{


}
