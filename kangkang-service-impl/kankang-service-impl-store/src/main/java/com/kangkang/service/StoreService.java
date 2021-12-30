package com.kangkang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.manage.entity.TbComment;
import com.kangkang.manage.viewObject.TbCommentVO;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.entity.TbStock;
import com.kangkang.store.viewObject.StoreSearchVO;
import com.kangkang.store.viewObject.TbStoreVO;
import com.kangkang.tools.PageUtils;

import java.util.List;

/**
 * @ClassName: StoreService
 * @Author: shaochunhai
 * @Date: 2021/8/12 9:56 上午
 * @Description: TODO
 */
public interface StoreService {
    List<TbStoreVO> queryStoreInfo(StoreSearchVO storeSearchVO);


    /**
     * 通过id查询商品详细信息
     * @param id
     * @return
     */
    TbStoreVO getStoreDetail(Long id);

    /**
     * 立即购买 获取商品实体数据
     * @param skuId
     * @return
     */
    TbSku getSkuData(Long skuId);

    /**
     * 查询库存
     * @param tbSkuId
     * @return
     */
    TbStock getStock(Long tbSkuId);


    /**
     * 通过主键获取sku商品主体信息
     * @param skuIds
     */
    List<TbSku> getSkuById(List<Long> skuIds);

    /**
     * 新增评论
     * @param tbCommentVO
     */
    void addComment(TbCommentVO tbCommentVO);

    /**
     * 查询累计评论
     * @param tbCommentVO
     * @return
     */
    Page<TbComment> queryCommentInfo(TbCommentVO tbCommentVO);

    /**
     * 点赞数
     *
     * @param flag 0-带表减1，1-代表加1
     * @param id  评论表的id
     * @return
     */
    Integer clickZAN(String flag, Long id);
}
