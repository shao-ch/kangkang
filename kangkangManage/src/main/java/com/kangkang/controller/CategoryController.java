package com.kangkang.controller;

import com.kangkang.manage.entity.TbCategory;
import com.kangkang.service.CategoryService;
import com.kangkang.tools.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
