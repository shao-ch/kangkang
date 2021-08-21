package com.kangkang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.store.entity.KangkangStore;
import com.kangkang.tools.PageUtils;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName: KangkangApiService
 * @Author: shaochunhai
 * @Date: 2021/8/12 11:49 上午
 * @Description: TODO
 */
public interface KangkangApiService {

    @LoadBalanced
    @PostMapping(value = "store/queryStoreInfo")
    Page<KangkangStore> queryStoreInfo(@RequestBody PageUtils pageUtils);
}
