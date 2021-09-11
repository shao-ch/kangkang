package com.kangkang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.dao.StoreDao;
import com.kangkang.dao.TbAfterSaleDao;
import com.kangkang.dao.TbSkuDao;
import com.kangkang.dao.TbStoreDetailDao;
import com.kangkang.service.StoreService;
import com.kangkang.store.entity.*;
import com.kangkang.store.viewObject.StoreDetailVO;
import com.kangkang.tools.PageUtils;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private TbAfterSaleDao tbAfterSaleDao;

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
    public StoreDetailVO getStoreDetail(Long id) {

        StoreDetailVO result = new StoreDetailVO();
        QueryWrapper<TbStoreDetail> wrapper = new QueryWrapper<>();
        //第一步查询商品详情实体
        wrapper.eq("id",id);
        TbStoreDetail tbStoreDetail = tbStoreDetailDao.selectOne(wrapper);
        //bean的转化
        BeanUtils.copyProperties(tbStoreDetail,result);
        //获取cid
        Long cid = tbStoreDetail.getTbCategoryId();
        //通过cid查询详情信息
        QueryWrapper<TbAfterSale> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("tb_category_id",cid);
        List<TbAfterSale> tbAfterSales = tbAfterSaleDao.selectList(wrapper1);
        //将售后详情数据放入结果中
        result.setTbAfterSales(tbAfterSales);
        return result;
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
