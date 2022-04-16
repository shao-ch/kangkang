package com.kangkang.ERP.controller;

import com.kangkang.ERP.CategoryFeign;
import com.kangkang.ERP.service.AcCategoryService;
import com.kangkang.manage.entity.TbCategory;
import com.kangkang.manage.viewObject.TbCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: CategoryImplController
 * @Author: shaochunhai
 * @Date: 2021/9/8 3:48 下午
 * @Description: TODO
 */
@RestController
public class CategoryImplController implements CategoryFeign {

    @Autowired
    private AcCategoryService acCategoryService;
    /**
     * 通过id查询分类信息
     * @param id
     * @return
     */
    @Override
    public List<TbCategory> queryCategoryInfo(Long id) {
        return acCategoryService.queryCategoryInfo(id);
    }

    /**
     * 保存分类目录
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCategory(TbCategoryVO vo) {
        acCategoryService.saveCategory(vo);
    }


    /**
     * 通过id删除分类条目
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(List<Long> ids) {
        acCategoryService.deleteCategory(ids);
    }
}
