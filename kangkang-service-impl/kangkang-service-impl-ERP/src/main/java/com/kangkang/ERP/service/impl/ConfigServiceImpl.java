package com.kangkang.ERP.service.impl;

import com.kangkang.ERP.dao.TbConfigInfoDao;
import com.kangkang.ERP.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public List<Map<String,String>> initConfigData() {

       final List<Map<String,String>> result = tbConfigInfoDao.queryAllConfig();
        return result;
    }

}
