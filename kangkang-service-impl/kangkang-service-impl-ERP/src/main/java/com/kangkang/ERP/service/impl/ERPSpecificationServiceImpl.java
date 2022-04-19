package com.kangkang.ERP.service.impl;

import com.kangkang.ERP.dao.SpecificationDao;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.ERP.service.ERPSpecificationService;
import com.kangkang.store.entity.TbSpecification;
import com.kangkang.store.viewObject.TbSpecificationVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: ERPSpecificationServiceImpl
 * @Author: shaochunhai
 * @Date: 2022/4/19 5:34 下午
 * @Description: TODO
 */
@Slf4j
@Service
public class ERPSpecificationServiceImpl implements ERPSpecificationService {

    @Autowired
    private SpecificationDao specificationDao;
    /**
     * 添加商品规格
     * @param tbSpecification
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addSpecification(TbSpecification tbSpecification) {

        specificationDao.insert(tbSpecification);
    }
    /**
     * 通过id更新规格
     * @param id
     */

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteSpecificationById(TbSpecification tbSpecification) {
        specificationDao.updateById(tbSpecification);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,rollbackFor = Exception.class)
    public Page<TbSpecification> querySpecification(TbSpecificationVO tbSpecificationVO) {
        Page<TbSpecification> page = new Page<>(tbSpecificationVO.getPageIndex(), tbSpecificationVO.getPageSize());
        /**
         * 查询规格分类信息
         */
        return specificationDao.querySpecification(page, tbSpecificationVO);
    }

    /**
     * 批量删除规格
     *
     * @param ids id的集合
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteSpecification(List<Long> ids) {
        specificationDao.deleteBatchIds(ids);
    }
}
