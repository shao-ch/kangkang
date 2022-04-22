package com.kangkang.ERP;

import com.kangkang.manage.entity.TbCategory;
import com.kangkang.manage.dtoObject.TbCategoryDTO;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @InterfaceName: CategoryFeign
 * @Author: shaochunhai
 * @Date: 2021/9/8 2:51 下午
 * @Description: TODO
 */
public interface CategoryFeign {

    @LoadBalanced
    @GetMapping("/category/queryCategoryInfo")
    List<TbCategory> queryCategoryInfo(@RequestParam("id") Long id);

    /**
     * 保存分类目录
     * @param vo
     * @return
     */
    @LoadBalanced
    @PostMapping("/category/saveCategory")
    void saveCategory(@RequestBody TbCategoryDTO vo);


    /**
     * 通过id删除分类条目
     * @param ids
     * @return
     */
    @LoadBalanced
    @PostMapping("/category/deleteCategory")
    void deleteCategory(@RequestParam("ids")List<Long> ids);
}
