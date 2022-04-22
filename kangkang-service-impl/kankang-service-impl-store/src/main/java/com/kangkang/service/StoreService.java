package com.kangkang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.manage.entity.TbComment;
import com.kangkang.manage.dtoObject.TbCommentDTO;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.entity.TbStock;
import com.kangkang.store.dtoObject.StoreSearchDTO;
import com.kangkang.store.dtoObject.TbStoreDTO;

import java.util.List;

/**
 * @ClassName: StoreService
 * @Author: shaochunhai
 * @Date: 2021/8/12 9:56 上午
 * @Description: TODO
 */
public interface StoreService {
    List<TbStoreDTO> queryStoreInfo(StoreSearchDTO storeSearchDTO);


    /**
     * 通过id查询商品详细信息
     * @param id
     * @return
     */
    TbStoreDTO getStoreDetail(Long id);

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
     * @param tbCommentDTO
     */
    void addComment(TbCommentDTO tbCommentDTO);

    /**
     * 查询累计评论
     * @param tbCommentDTO
     * @return
     */
    Page<TbComment> queryCommentInfo(TbCommentDTO tbCommentDTO);

    /**
     * 点赞数
     *
     * @param flag 0-带表减1，1-代表加1
     * @param id  评论表的id
     * @return
     */
    Integer clickZAN(String flag, Long id);
}
