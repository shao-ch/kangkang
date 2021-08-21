package com.kangkang.service;

import com.kangkang.manage.entity.KangkangUser;
import com.kangkang.manage.viewObject.TbAdressVO;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @InterfaceName: KangkangFeign
 * @Author: shaochunhai
 * @Date: 2021/8/8 5:08 下午
 * @Description: TODO
 */
public interface KangkangFeign {

    /**
     * 保存用户
     * @param kangkangUser
     * @return
     */
    @LoadBalanced
    @PostMapping(value = "manage/save/")
    ResponseEntity<String> save(@RequestBody KangkangUser kangkangUser);

    /**
     * 用户查询
     * @param kangkangUser
     * @return
     */
    @LoadBalanced
    @PostMapping(value = "manage/selectUser/")
    KangkangUser selectUser(KangkangUser kangkangUser);


    /**
     * 获取收货地址
     * @param id  用户id
     * @return
     */
    @LoadBalanced
    @GetMapping("/manage/selectAddress/")
    List<TbAdressVO> selectAddress(Integer id);
}
