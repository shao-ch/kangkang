package com.kangkang.service;

import com.kangkang.entity.KangkangUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
}
