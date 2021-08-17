package com.kangkang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kangkang.entity.KangkangStore;
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
