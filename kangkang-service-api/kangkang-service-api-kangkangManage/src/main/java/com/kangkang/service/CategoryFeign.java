package com.kangkang.service;

import com.kangkang.manage.entity.TbCategory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @InterfaceName: CategoryFeign
 * @Author: shaochunhai
 * @Date: 2021/9/8 2:51 下午
 * @Description: TODO
 */
public interface CategoryFeign {

    @LoadBalanced
    @GetMapping("/category/queryCategoryInfo")
    List<TbCategory> queryCategoryInfo(@RequestParam("id") Long id);
}
