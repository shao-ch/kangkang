package com.kangkang.ERP.service;

import com.kangkang.manage.entity.TbErpUser;

/**
 * @ClassName: ERPUserService
 * @Author: shaochunhai
 * @Date: 2022/2/28 1:45 下午
 * @Description: TODO
 */

public interface ERPUserService {
    TbErpUser selectErpUser(TbErpUser tbErpUser);

    /**
     * 用户注册信息
     * @param tbUser
     * @return
     */
    TbErpUser save(TbErpUser tbErpUser);
}
