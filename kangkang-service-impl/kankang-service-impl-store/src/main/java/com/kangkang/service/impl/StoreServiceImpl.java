package com.kangkang.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.dao.StoreDao;
import com.kangkang.entity.A;
import com.kangkang.entity.KangkangStore;
import com.kangkang.service.StoreService;
import com.kangkang.tools.ConverUtils;
import com.kangkang.tools.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: StoreServiceImpl
 * @Author: shaochunhai
 * @Date: 2021/8/12 9:56 上午
 * @Description: TODO
 */
@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreDao storeDao;
    @Override
    public Page<KangkangStore> queryStoreInfo(PageUtils pageUtils) {
        Page<KangkangStore> page = new Page<>(pageUtils.getPageIndex(),pageUtils.getPageSize());
        return storeDao.selectPage(page,null);
    }
}
