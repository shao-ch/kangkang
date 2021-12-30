package com.kangkang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.manage.entity.TbComment;
import com.kangkang.manage.viewObject.TbCommentVO;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.entity.TbStock;
import com.kangkang.store.viewObject.StoreSearchVO;
import com.kangkang.store.viewObject.TbStoreVO;
import com.kangkang.tools.PageUtils;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName: KangkangApiService
 * @Author: shaochunhai
 * @Date: 2021/8/12 11:49 上午
 * @Description: TODO
 */
public interface KangkangApiService {

    @LoadBalanced
    @PostMapping(value = "store/queryStoreInfo")
    List<TbStoreVO> queryStoreInfo(@RequestBody StoreSearchVO storeSearchVO);


    @LoadBalanced
    @GetMapping(value = "store/getStoreDetail")
    TbStoreVO getStoreDetail(@RequestParam("id") Long id);


    /**
     * 立即购买 获取商品实体数据
     * @param skuId
     * @return
     */
    @LoadBalanced
    @GetMapping(value = "store/getSkuData")
    TbSku getSkuData(@RequestParam("skuId") Long skuId);

    /**
     * 查询库存
     * @param tbSkuId
     * @return
     */
    @LoadBalanced
    @GetMapping(value = "store/getStock")
    TbStock getStock(Long tbSkuId);

    /**
     * 通过主键获取sku商品主体信息
     * @param skuId
     */
    @LoadBalanced
    @GetMapping(value = "store/getSkuById")
    List<TbSku> getSkuById(@RequestParam("tbStoreId") List<Long> skuId);

    /**
     * 新增评论
     * @param tbCommentVO
     */
    @LoadBalanced
    @PostMapping(value = "store/addComment")
    void addComment(@RequestBody TbCommentVO tbCommentVO);

    /**
     * 查询累计评论
     * @param tbCommentVO
     * @return
     */
    @LoadBalanced
    @GetMapping(value = "store/queryCommentInfo")
    Page<TbComment> queryCommentInfo(@RequestBody TbCommentVO tbCommentVO);

    /**
     * 点赞数
     *
     * @param flag 0-带表减1，1-代表加1
     * @param id  评论表的id
     * @return
     */
    @LoadBalanced
    @GetMapping(value = "store/clickZAN")
    Integer clickZAN(@RequestParam("flag") String flag, @RequestParam("id") Long id);
}
