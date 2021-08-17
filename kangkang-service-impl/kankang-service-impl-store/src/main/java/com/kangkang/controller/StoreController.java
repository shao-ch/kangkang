package com.kangkang.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.entity.KangkangStore;
import com.kangkang.service.KangkangApiService;
import com.kangkang.service.StoreService;
import com.kangkang.tools.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName: StoreController
 * @Author: shaochunhai
 * @Date: 2021/8/12 9:48 上午
 * @Description: TODO
 */
@RestController
public class StoreController implements KangkangApiService {

    @Resource
    private StoreService storeService;
    @Override
    public Page<KangkangStore> queryStoreInfo(PageUtils pageUtils) {
        return storeService.queryStoreInfo(pageUtils);
    }
}
