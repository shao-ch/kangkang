package com.kangkang.ERP.login.service;

import com.kangkang.ERP.ERPUserFeign;
import com.kangkang.manage.entity.TbErpUser;
import com.kangkang.manage.entity.TbUser;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @InterfaceName: ERPUserService
 * @Author: shaochunhai
 * @Date: 2022/2/28 11:29 上午
 * @Description: TODO
 */
@FeignClient(value = "kangkang-manage")
public interface ERPUserService extends ERPUserFeign {
}
