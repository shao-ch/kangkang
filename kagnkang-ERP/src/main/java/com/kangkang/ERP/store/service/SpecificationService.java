package com.kangkang.ERP.store.service;

import com.kangkang.ERP.SpecificationFeign;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @InterfaceName: tbSpecificationService
 * @Author: shaochunhai
 * @Date: 2022/4/19 5:22 下午
 * @Description: TODO
 */

//fallback类似与一个回调，当调用失败，或者网络不同的情况下，返回这个类中的内容
//@FeignClient(value = "kangkang-manage",url = "指定调用那个ip下的服务",fallback = CategoryFallBack.class)
@FeignClient(value = "kangkang-ERP-impl")
public interface SpecificationService extends SpecificationFeign {
}
