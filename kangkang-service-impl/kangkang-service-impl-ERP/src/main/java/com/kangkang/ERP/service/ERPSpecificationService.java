package com.kangkang.ERP.service;

import com.kangkang.store.entity.TbSpecification;
import com.kangkang.store.dtoObject.TbSpecificationDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @InterfaceName: ERPSpecificationService
 * @Author: shaochunhai
 * @Date: 2022/4/19 5:34 下午
 * @Description: TODO
 */

public interface ERPSpecificationService {

    /**
     * 添加商品规格
     * @param tbSpecificationVO
     * @return
     */
    void addSpecification(TbSpecification tbSpecification);

    /**
     * 通过id更新规格
     * @param id
     */
    void deleteSpecificationById(TbSpecification tbSpecification);

    /**
     * 查询商品规格
     * @param tbSpecificationDTO
     */
    Page<TbSpecification> querySpecification(TbSpecificationDTO tbSpecificationDTO);

    /**
     * 批量删除规格
     *
     * @param ids id的集合
     * @return
     */
    void deleteSpecification(List<Long> ids);
}
