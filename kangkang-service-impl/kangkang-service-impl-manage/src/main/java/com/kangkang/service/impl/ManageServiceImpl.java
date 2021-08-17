package com.kangkang.service.impl;

import com.kangkang.dao.KangKangUserDao;
import com.kangkang.entity.KangkangUser;
import com.kangkang.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: ManageServiceImpl
 * @Author: shaochunhai
 * @Date: 2021/8/8 3:56 下午
 * @Description: TODO
 */
@Service("manageService")
public class ManageServiceImpl implements ManageService {


    @Autowired
    private KangKangUserDao kangKangUserDao;
    /**
     * 注册账号
     * @param kangkangUser
     */
    @Override
    @Transactional
    public void save(KangkangUser kangkangUser) {

        kangKangUserDao.insert(kangkangUser);
    }
}
