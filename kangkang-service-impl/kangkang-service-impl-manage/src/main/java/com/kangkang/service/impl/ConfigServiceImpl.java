package com.kangkang.service.impl;

import com.kangkang.dao.TbConfigInfoDao;
import com.kangkang.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @ClassName: ConfigServiceImpl
 * @Author: shaochunhai
 * @Date: 2021/9/22 10:49 上午
 * @Description: TODO
 */
@Slf4j
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private TbConfigInfoDao tbConfigInfoDao;

    @Autowired


    /**
     * 查询所有配置类信息
     *
     * @return
     */
    @Override
    public HashMap<String, String> initConfigData() {

       final HashMap<String, String> result = tbConfigInfoDao.queryAllConfig();
        return result;
    }

}
