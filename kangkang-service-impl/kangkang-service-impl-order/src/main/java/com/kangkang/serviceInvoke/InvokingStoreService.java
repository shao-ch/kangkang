package com.kangkang.serviceInvoke;

import com.kangkang.service.KangkangApiService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName: InvokingStoreService  远程调用kangkang-store-service  微服务
 * @Author: shaochunhai
 * @Date: 2021/8/27 9:30 上午
 * @Description: TODO
 */
@FeignClient("kangkang-store-service")
public interface InvokingStoreService extends KangkangApiService {
}
