package com.kangkang.service.impl;

import com.kangkang.dao.TbCategoryDao;
import com.kangkang.manage.entity.TbCategory;
import com.kangkang.service.AcCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: AcCategoryServiceImpl
 * @Author: shaochunhai
 * @Date: 2021/9/8 3:52 下午
 * @Description: TODO
 */
@Slf4j
@Service
public class AcCategoryServiceImpl implements AcCategoryService {

    @Autowired
    private TbCategoryDao tbCategoryDao;
    @Override
    public List<TbCategory> queryCategoryInfo(Long id) {
        return tbCategoryDao.queryCategoryInfoByParendId(id);
    }
}
