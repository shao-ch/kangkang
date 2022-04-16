package com.kangkang.ERP.login.service;

import com.kangkang.ERP.ERPUserFeign;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @InterfaceName: ERPUserService
 * @Author: shaochunhai
 * @Date: 2022/2/28 11:29 上午
 * @Description: TODO
 */
@FeignClient(value = "kangkang-ERP-impl")
public interface ERPUserService extends ERPUserFeign {
}
