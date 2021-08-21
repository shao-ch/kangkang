package com.kangkang.service;


import com.kangkang.manage.entity.KangkangUser;
import com.kangkang.manage.viewObject.TbAdressVO;

import java.util.List;

/**
 * @ClassName: ManageService
 * @Author: shaochunhai
 * @Date: 2021/8/8 3:56 下午
 * @Description: TODO
 */
public interface ManageService {

    /**
     * 注册账号
     * @param kangkangUser
     */
    void save(KangkangUser kangkangUser);

    /**
     * 查询用户
     * @param kangkangUser
     * @return
     */
    KangkangUser selectUser(KangkangUser kangkangUser);

    /**
     * 获取收货地址
     * @param id  用户id
     * @return
     */
    List<TbAdressVO> selectAddress(Integer id);
}
