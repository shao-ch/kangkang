package com.kangkang.service;


import com.kangkang.manage.entity.TbUser;
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
     * @param tbUser
     */
    void save(TbUser tbUser);

    /**
     * 查询用户
     * @param tbUser
     * @return
     */
    TbUser selectUser(TbUser tbUser);

    /**
     * 获取收货地址
     * @param id  用户id
     * @return
     */
    List<TbAdressVO> selectAddress(Integer id);
}
