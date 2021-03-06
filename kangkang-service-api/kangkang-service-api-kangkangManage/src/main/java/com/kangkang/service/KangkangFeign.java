package com.kangkang.service;

import com.kangkang.manage.entity.TbAddress;
import com.kangkang.manage.entity.TbUser;
import com.kangkang.manage.dtoObject.TbAdressDTO;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName: KangkangFeign
 * @Author: shaochunhai
 * @Date: 2021/8/8 5:08 下午
 * @Description: TODO
 */
public interface KangkangFeign {

    /**
     * 保存用户
     * @param tbUser
     * @return
     */
    @LoadBalanced
    @PostMapping(value = "manage/save/")
    ResponseEntity<String> save(@RequestBody TbUser tbUser);

    /**
     * 用户查询
     * @param tbUser
     * @return
     */
    @LoadBalanced
    @PostMapping(value = "manage/selectUser/")
    TbUser selectUser(@RequestBody TbUser tbUser);


    /**
     * 获取收货地址
     * @param id  用户id
     * @return
     */
    @LoadBalanced
    @GetMapping("/manage/selectAddress/")
    List<TbAddress> selectAddress(@RequestParam("id") String id);


    /**
     * 新增收货地址
     * @param vo
     * @return
     */
    @LoadBalanced
    @PostMapping("/manage/commitAddress/")
    Map<String,Object> commitAddress(@RequestBody TbAdressDTO vo);


    /**
     * 删除地址
     * @param vo
     * @return
     */
    @LoadBalanced
    @PostMapping("/manage/deleteAddress/")
    void deleteAddress(@RequestBody TbAdressDTO vo);
}
