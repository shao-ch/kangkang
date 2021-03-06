package com.kangkang.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.manage.entity.TbComment;
import com.kangkang.manage.dtoObject.TbCommentDTO;
import com.kangkang.service.StoreService;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.entity.TbStock;
import com.kangkang.store.dtoObject.StoreSearchDTO;
import com.kangkang.store.dtoObject.TbStoreDTO;
import com.kangkang.tools.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: StoreController
 * @Author: shaochunhai
 * @Date: 2021/8/12 9:39 上午
 * @Description: TODO
 */
@RestController
@RequestMapping()
public class StoreController {

    @Autowired
    private StoreService storeService;


    /**
     * 查询商品信息
     *
     * @return
     */
    @PostMapping("/queryStoreInfo")
    public ResponseCode<List<TbStoreDTO>> queryStoreInfo(@RequestBody StoreSearchDTO storeSearchDTO) {

        List<TbStoreDTO> list = null;
        try {
            list = storeService.queryStoreInfo(storeSearchDTO);
            return ResponseCode.message(200, list, "success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.message(500, null, "服务异常");
        }
    }

    /**
     * 通过id查询商品详细信息 PathVariable  这个注解是用来接受路径后面的参数的
     *
     * @param id
     * @return
     */
    @GetMapping("/getStoreDetail")
    public ResponseCode<TbStoreDTO> getStoreDetail(@RequestParam("id") Long id) {

        TbStoreDTO tbStoreDetail = null;
        try {
            tbStoreDetail = storeService.getStoreDetail(id);
            return ResponseCode.message(200, tbStoreDetail, "success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.message(500, null, "服务异常");
        }
    }


    /**
     * 立即购买 获取商品实体数据
     *
     * @param skuId tb_sku的主键
     * @return
     */
    @GetMapping("/getSkuData")
    public ResponseCode<TbSku> getSkuData(@RequestParam("skuId") Long skuId) {

        TbSku result = null;
        try {
            result = storeService.getSkuData(skuId);
            return ResponseCode.message(200, result, "success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.message(500, null, "服务异常");
        }
    }


    /**
     * 查询库存
     *
     * @param tbSkuId tb_sku的主键
     * @return
     */
    @GetMapping("/getStock")
    public ResponseCode<TbStock> getStock(@RequestParam("id") Long tbSkuId) {

        TbStock tbStock = null;
        try {
            tbStock = storeService.getStock(tbSkuId);
            return ResponseCode.message(200, tbStock, "success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.message(500, null, "服务异常");
        }
    }

    /**
     * 新增评论
     *
     * @param tbCommentDTO 评论消息
     * @return
     */
    @PostMapping("/addComment")
    public ResponseCode<Void> addComment(@RequestBody TbCommentDTO tbCommentDTO) {

        if (tbCommentDTO.getTbStoreId() == null) {
            return ResponseCode.message(500, null, "商品id不能为空");
        }
        try {
            storeService.addComment(tbCommentDTO);
            return ResponseCode.message(200, null, "success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.message(500, null, "服务异常");
        }
    }


    /**
     * 查询累计评论
     *
     * @param tbCommentDTO 评论消息
     * @return
     */
    @GetMapping("/queryCommentInfo")
    public ResponseCode<Page<TbComment>> queryCommentInfo(@RequestBody TbCommentDTO tbCommentDTO) {

        if (tbCommentDTO.getTbStoreId() == null) {
            return ResponseCode.message(500, null, "商品id不能为空");
        }
        Page<TbComment> list = null;
        try {
            list = storeService.queryCommentInfo(tbCommentDTO);
            return ResponseCode.message(200, list, "success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.message(500, null, "服务异常");
        }
    }


    /**
     * 点赞数
     *
     * @param flag 0-带表减1，1-代表加1
     * @param id  评论表的id
     * @return
     */
    @GetMapping("/clickZAN")
    public ResponseCode<Integer> clickZAN(@RequestParam(value = "flag", required = false) String flag,
                                          @RequestParam(value = "id", required = false) Long id) {

        Integer conut = null;
        try {
            conut = storeService.clickZAN(flag, id);
            return ResponseCode.message(200, conut, "success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.message(500, null, "服务异常");
        }
    }

}
