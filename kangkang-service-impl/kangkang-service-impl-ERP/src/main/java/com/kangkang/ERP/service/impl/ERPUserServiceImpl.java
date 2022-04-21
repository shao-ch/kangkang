package com.kangkang.ERP.service.impl;

import com.kangkang.ERP.dao.TbErpUserDao;
import com.kangkang.ERP.service.ERPUserService;
import com.kangkang.manage.entity.TbErpUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: ERPUserServiceImpl
 * @Author: shaochunhai
 * @Date: 2022/2/28 1:46 下午
 * @Description: TODO
 */
@Slf4j
@Service
public class ERPUserServiceImpl implements ERPUserService {

    @Autowired
    private TbErpUserDao tbErpUserDao;
    @Override
    public TbErpUser selectErpUser(TbErpUser tbErpUser) {
        return tbErpUserDao.selectErpUser(tbErpUser);
    }


    /**
     * 用户注册信息
     * @param tbErpUser
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public TbErpUser save(TbErpUser tbErpUser) {
        tbErpUserDao.insert(tbErpUser);
        return tbErpUser;
    }

    /**
     * 通过用户名和密码查询
     * @param tbErpUser
     * @return
     */
    @Override
    public TbErpUser selectErpByUserAndPassword(TbErpUser tbErpUser) {
        return tbErpUserDao.selectErpByUserAndPassword(tbErpUser);
    }
}
