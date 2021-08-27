package com.kangkang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.entity.TbStock;
import com.kangkang.store.entity.TbStore;
import com.kangkang.store.entity.TbStoreDetail;
import com.kangkang.store.viewObject.StoreDetailVO;
import com.kangkang.tools.PageUtils;

import java.util.List;

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

    /**
     * 立即购买 获取商品实体数据
     * @param tbStoreId
     * @return
     */
    List<TbSku> getSkuData(Long tbStoreId);

    /**
     * 查询库存
     * @param tbSkuId
     * @return
     */
    TbStock getStock(Long tbSkuId);


    /**
     * 通过主键获取sku商品主体信息
     * @param skuId
     */
    TbSku getSkuById(Long skuId);
}
