package com.kangkang.service.impl;

import com.kangkang.dao.TbCategoryDao;
import com.kangkang.manage.entity.TbCategory;
import com.kangkang.manage.viewObject.TbCategoryVO;
import com.kangkang.service.AcCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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


    /**
     * 保存分类目录
     * @param vo
     * @return
     */
    @Override
    public void saveCategory(TbCategoryVO vo) {

        //0代表一级目录  1代表其他子目录
        if (StringUtils.equals("0", vo.getFlag())) {
            List<TbCategory> list = vo.getCategoryList();
            //数据到分类表
            list.stream().forEach(tbCategory ->
                    tbCategoryDao.insert(tbCategory));
        } else {
            vo.getCategoryList().stream().forEach(category ->
                    tbCategoryDao.insert(category));
        }
    }

    /**
     * 通过id删除分类条目
     * @param ids
     * @return
     */
    @Override
    public void deleteCategory(List<Long> ids) {
        ids.stream().forEach(id->tbCategoryDao.deleteById(id));
    }
}
