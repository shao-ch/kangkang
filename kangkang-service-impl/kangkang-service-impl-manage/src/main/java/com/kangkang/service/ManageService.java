package com.kangkang.service;


import com.kangkang.manage.entity.TbAddress;
import com.kangkang.manage.entity.TbUser;
import com.kangkang.manage.viewObject.TbAdressVO;

import java.util.List;
import java.util.Map;

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
     * 通过openId查询用户地址
     * @param openId
     * @return
     */
    List<TbAddress> selectAddress(String openId);

    /**
     * 新增收货地址
     * @param vo
     */
    Map<String,Object> commitAddress(TbAdressVO vo);

    /**
     * 删除地址
     * @param vo
     * @return
     */
    void deleteAddress(TbAdressVO vo);
}
