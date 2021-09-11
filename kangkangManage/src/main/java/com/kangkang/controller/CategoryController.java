package com.kangkang.controller;

import com.alibaba.fastjson.JSONObject;
import com.kangkang.manage.entity.TbCategory;
import com.kangkang.manage.viewObject.TbCategoryVO;
import com.kangkang.service.CategoryService;
import com.kangkang.tools.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: CategoryController  分类信息
 * @Author: shaochunhai
 * @Date: 2021/9/8 2:42 下午
 * @Description: TODO
 */
@Slf4j
@RestController
@RequestMapping("/cate")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 通过id查询分类列表
     * @param id
     * @return
     */
    @GetMapping("/queryCategoryInfo")
    public ResponseCode<List<TbCategory>> queryCategoryInfo(@RequestParam("id") Long id){
        List<TbCategory> result=null;
        try {
            result=categoryService.queryCategoryInfo(id);
        } catch (Exception e) {
            log.error("=====服务异常======"+e);
            return ResponseCode.message(500,null,"服务异常");
        }

        return ResponseCode.message(200,result,"success");
    }

    /**
     * 保存分类目录
     * @param vo
     * @return
     */
    @PostMapping("/saveCategory")
    public ResponseCode<Void> saveCategory(@RequestBody TbCategoryVO vo){
        log.info("====开始保存分类信息=====参数为："+ JSONObject.toJSONString(vo));
        try {
            categoryService.saveCategory(vo);
        } catch (Exception e) {
            log.error("=====服务异常======"+e);
            return ResponseCode.message(500,null,"服务异常");
        }

        return ResponseCode.message(200,null,"success");
    }

    /**
     * 删除分类  (不仅仅删除该分类，还会删除其子目录)
     * @param ids
     * @return
     */
    @DeleteMapping ("/deleteCategory")
    public ResponseCode<Void> deleteCategory(@RequestParam("ids") List<Long> ids){
        log.info("====开始删除分类=====参数为："+ JSONObject.toJSONString(ids));
        try {
            categoryService.deleteCategory(ids);
        } catch (Exception e) {
            log.error("=====服务异常======"+e);
            return ResponseCode.message(500,null,"服务异常");
        }

        return ResponseCode.message(200,null,"success");
    }


}
