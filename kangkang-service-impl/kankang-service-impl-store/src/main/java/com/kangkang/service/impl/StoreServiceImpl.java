package com.kangkang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.dao.StoreDao;
import com.kangkang.dao.TbSkuDao;
import com.kangkang.dao.TbStoreDetailDao;
import com.kangkang.service.StoreService;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.entity.TbStock;
import com.kangkang.store.entity.TbStore;
import com.kangkang.store.entity.TbStoreDetail;
import com.kangkang.store.viewObject.StoreDetailVO;
import com.kangkang.tools.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private TbStoreDetailDao tbStoreDetailDao;

    @Autowired
    private TbSkuDao tbSkuDao;
    @Override
    @Transactional(readOnly = true)
    public Page<TbStore> queryStoreInfo(PageUtils pageUtils) {
        Page<TbStore> page = new Page<>(pageUtils.getPageIndex(),pageUtils.getPageSize());
        return storeDao.selectPage(page,null);
    }


    /**
     * 通过id查询商品详细信息
     * @param id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)   //这里要设置只读
    public TbStoreDetail getStoreDetail(Long id) {

        QueryWrapper<TbStoreDetail> wrapper = new QueryWrapper<>();

        //第一步查询商品详情实体
        wrapper.eq("id",id);
        return tbStoreDetailDao.selectOne(wrapper);
    }


    /**
     * 立即购买 获取商品实体数据
     * @param tbStoreId
     * @return
     */
    @Override
    public List<TbSku> getSkuData(Long tbStoreId) {
        QueryWrapper<TbSku> wrapper = new QueryWrapper<>();

        wrapper.eq("tb_store_id",tbStoreId);

        return tbSkuDao.selectList(wrapper);
    }


    /**
     * 查询库存
     * @param tbSkuId
     * @return
     */
    @Override
    public TbStock getStock(Long tbSkuId) {
        return tbSkuDao.getStock(tbSkuId);
    }

    /**
     * 通过主键获取sku商品主体信息
     * @param skuIds
     */
    @Override
    public List<TbSku> getSkuById(List<Long> skuIds) {
        return tbSkuDao.selectBatchIds(skuIds);
    }
}
