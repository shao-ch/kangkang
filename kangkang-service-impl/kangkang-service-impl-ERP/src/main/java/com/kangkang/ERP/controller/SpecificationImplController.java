package com.kangkang.ERP.controller;

import com.kangkang.ERP.SpecificationFeign;
import com.kangkang.ERP.service.ERPSpecificationService;
import com.kangkang.store.entity.TbSpecification;
import com.kangkang.store.viewObject.TbSpecificationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: SpecificationImplController  规格参数具体逻辑实现
 * @Author: shaochunhai
 * @Date: 2022/4/19 5:32 下午
 * @Description: TODO
 */
@Slf4j
@RestController
public class SpecificationImplController implements SpecificationFeign {

    @Autowired
    private ERPSpecificationService erpSpecificationService;
    /**
     * 添加商品规格
     * @param tbSpecification
     * @return
     */
    @Override
    public void addSpecification(TbSpecification tbSpecification) {

        erpSpecificationService.addSpecification(tbSpecification);
    }

    /**
     * 通过id更新规格
     * @param tbSpecification
     */
    @Override
    public void updateSpecificationById(TbSpecification tbSpecification) {
        erpSpecificationService.deleteSpecificationById(tbSpecification);
    }

    /**
     * 查询商品规格
     * @param tbSpecificationVO
     */
    @Override
    public Page<TbSpecification> querySpecification(TbSpecificationVO tbSpecificationVO) {
        return erpSpecificationService.querySpecification(tbSpecificationVO);
    }

    /**
     * 批量删除规格
     *
     * @param ids id的集合
     * @return
     */
    @Override
    public void deleteSpecification(List<Long> ids) {
        erpSpecificationService.deleteSpecification(ids);
    }
}
