package com.kangkang.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.service.KangkangApiService;
import com.kangkang.service.StoreService;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.entity.TbStock;
import com.kangkang.store.entity.TbStore;
import com.kangkang.store.entity.TbStoreDetail;
import com.kangkang.store.viewObject.StoreDetailVO;
import com.kangkang.tools.PageUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
    public Page<TbStore> queryStoreInfo(PageUtils pageUtils) {
        return storeService.queryStoreInfo(pageUtils);
    }


    /**
     * 通过id查询商品详细信息
     * @param id
     * @return
     */
    @Override
    public TbStoreDetail getStoreDetail(Long id) {
        return storeService.getStoreDetail(id);
    }


    /**
     * 立即购买 获取商品实体数据
     * @param tbStoreId
     * @return
     */
    @Override
    public List<TbSku> getSkuData(Long tbStoreId) {
        return storeService.getSkuData(tbStoreId);
    }

    /**
     * 查询库存
     * @param tbSkuId
     * @return
     */
    @Override
    public TbStock getStock(Long tbSkuId) {
        return storeService.getStock(tbSkuId);
    }

    /**
     * 通过主键获取sku商品主体信息
     * @param skuIds
     */
    @Override
    public List<TbSku> getSkuById(List<Long> skuIds) {
        return storeService.getSkuById(skuIds);
    }
}
