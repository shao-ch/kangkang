package com.kangkang.ERP.service;

import com.kangkang.manage.entity.TbCategory;
import com.kangkang.manage.dtoObject.TbCategoryDTO;

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

    /**
     * 保存分类目录
     * @param vo
     * @return
     */
    void saveCategory(TbCategoryDTO vo);

    /**
     * 通过id删除分类条目
     * @param ids
     * @return
     */
    void deleteCategory(List<Long> ids);
}
