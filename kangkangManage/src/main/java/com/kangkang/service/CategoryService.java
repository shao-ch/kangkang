package com.kangkang.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @InterfaceName: CategoryService  分类服务
 * @Author: shaochunhai
 * @Date: 2021/9/8 2:50 下午
 * @Description: TODO
 */
@FeignClient(value = "kangkang-manage")
public interface CategoryService extends CategoryFeign{
}
