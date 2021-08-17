package com.kangkang.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @InterfaceName: UserService  用户表
 * @Author: shaochunhai
 * @Date: 2021/8/8 2:46 下午
 * @Description: TODO
 */
@FeignClient(value = "kangkang-manage")
public interface UserService extends KangkangFeign{

}
