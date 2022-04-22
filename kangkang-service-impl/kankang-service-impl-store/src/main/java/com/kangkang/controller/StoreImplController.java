package com.kangkang.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.manage.entity.TbComment;
import com.kangkang.manage.dtoObject.TbCommentDTO;
import com.kangkang.service.KangkangApiService;
import com.kangkang.service.StoreService;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.entity.TbStock;
import com.kangkang.store.dtoObject.StoreSearchDTO;
import com.kangkang.store.dtoObject.TbStoreDTO;
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
public class StoreImplController implements KangkangApiService {

    @Resource
    private StoreService storeService;
    @Override
    public List<TbStoreDTO> queryStoreInfo(StoreSearchDTO storeSearchDTO) {
        return storeService.queryStoreInfo(storeSearchDTO);
    }


    /**
     * 通过id查询商品详细信息
     * @param id
     * @return
     */
    @Override
    public TbStoreDTO getStoreDetail(Long id) {
        return storeService.getStoreDetail(id);
    }


    /**
     * 立即购买 获取商品实体数据
     * @param skuId
     * @return
     */
    @Override
    public TbSku getSkuData(Long skuId) {
        return storeService.getSkuData(skuId);
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

    /**
     * 新增评论
     * @param tbCommentDTO
     */
    @Override
    public void addComment(TbCommentDTO tbCommentDTO) {
        storeService.addComment(tbCommentDTO);
    }


    /**
     * 查询累计评论
     * @param tbCommentDTO
     * @return
     */
    @Override
    public Page<TbComment> queryCommentInfo(TbCommentDTO tbCommentDTO) {

        return storeService.queryCommentInfo(tbCommentDTO);
    }

    /**
     * 点赞数
     *
     * @param flag 0-带表减1，1-代表加1
     * @param id  评论表的id
     * @return
     */
    @Override
    public Integer clickZAN(String flag, Long id) {
        return storeService.clickZAN(flag,id);
    }
}
