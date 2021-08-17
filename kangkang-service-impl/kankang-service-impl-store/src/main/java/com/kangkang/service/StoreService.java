package com.kangkang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.entity.KangkangStore;
import com.kangkang.tools.PageUtils;
import org.springframework.stereotype.Service;

/**
 * @ClassName: StoreService
 * @Author: shaochunhai
 * @Date: 2021/8/12 9:56 上午
 * @Description: TODO
 */
public interface StoreService {
    Page<KangkangStore> queryStoreInfo(PageUtils pageUtils);


}
