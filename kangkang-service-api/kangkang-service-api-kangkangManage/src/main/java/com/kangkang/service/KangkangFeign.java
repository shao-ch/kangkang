package com.kangkang.service;

import com.kangkang.entity.KangkangUser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

/**
 * @InterfaceName: KangkangFeign
 * @Author: shaochunhai
 * @Date: 2021/8/8 5:08 下午
 * @Description: TODO
 */
public interface KangkangFeign {

    @LoadBalanced
    @PostMapping(value = "manage/save/")
    ResponseEntity<String> save(@RequestBody KangkangUser kangkangUser);

    @GetMapping(value = "manage/bbb/")
    String bbb(@PathVariable("id")int id);
}
