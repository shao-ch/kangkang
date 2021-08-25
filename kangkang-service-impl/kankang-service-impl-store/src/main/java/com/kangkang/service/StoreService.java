package com.kangkang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.store.entity.TbStore;
import com.kangkang.store.entity.TbStoreDetail;
import com.kangkang.store.viewObject.StoreDetailVO;
import com.kangkang.tools.PageUtils;

/**
 * @ClassName: StoreService
 * @Author: shaochunhai
 * @Date: 2021/8/12 9:56 上午
 * @Description: TODO
 */
public interface StoreService {
    Page<TbStore> queryStoreInfo(PageUtils pageUtils);


    /**
     * 通过id查询商品详细信息
     * @param id
     * @return
     */
    TbStoreDetail getStoreDetail(Long id);
}
