package com.kangkang.fallback;

import com.kangkang.manage.entity.TbCategory;
import com.kangkang.manage.viewObject.TbCategoryVO;
import com.kangkang.service.CategoryFeign;
import com.kangkang.service.CategoryService;
import feign.hystrix.FallbackFactory;

import java.util.List;

/**
 * @ClassName: CategoryFallBack  网络不同的回调接口
 * @Author: shaochunhai
 * @Date: 2022/3/19 10:55 下午
 * @Description: TODO
 */
public class CategoryFallBack implements CategoryService {
    @Override
    public List<TbCategory> queryCategoryInfo(Long id) {
        return null;
    }

    @Override
    public void saveCategory(TbCategoryVO vo) {

    }

    @Override
    public void deleteCategory(List<Long> ids) {

    }
}
