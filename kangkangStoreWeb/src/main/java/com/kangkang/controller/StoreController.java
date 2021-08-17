package com.kangkang.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.entity.KangkangStore;
import com.kangkang.service.StoreService;
import com.kangkang.tools.HttpStatusCode;
import com.kangkang.tools.PageUtils;
import com.kangkang.tools.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: StoreController
 * @Author: shaochunhai
 * @Date: 2021/8/12 9:39 上午
 * @Description: TODO
 */
@RestController
@RequestMapping("store")
public class StoreController {

    @Autowired
    private StoreService storeService;


    /**
     * 查询商品信息
     * @return
     */
    @PostMapping("/queryStoreInfo")
    public ResponseCode<IPage> queryStoreInfo(@RequestBody PageUtils pageUtils){

        Page<KangkangStore> page= null;
        try {
            page = storeService.queryStoreInfo(pageUtils);
            return ResponseCode.message(200,page,"success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.message(500,null,"服务异常");
        }
    }
}
