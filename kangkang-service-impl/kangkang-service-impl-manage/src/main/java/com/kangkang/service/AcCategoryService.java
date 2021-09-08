package com.kangkang.service;

import com.kangkang.manage.entity.TbCategory;

import java.util.List;

/**
 * @ClassName: AcCategoryService
 * @Author: shaochunhai
 * @Date: 2021/9/8 3:52 下午
 * @Description: TODO
 */
public interface AcCategoryService {
    /**
     * 通过id查询分类信息
     * @param id
     * @return
     */
    List<TbCategory> queryCategoryInfo(Long id);
}
