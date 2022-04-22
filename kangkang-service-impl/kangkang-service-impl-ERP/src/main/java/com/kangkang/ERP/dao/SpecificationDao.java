package com.kangkang.ERP.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.store.entity.TbSpecification;
import com.kangkang.store.dtoObject.TbSpecificationDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: SpecificationDao  规格表的dao层
 * @Author: shaochunhai
 * @Date: 2022/4/19 5:38 下午
 * @Description: TODO
 */
@Repository
public interface SpecificationDao extends BaseMapper<TbSpecification> {

    /**
     * 分页查询规格数据
     * @param page
     * @param tbSpecificationDTO
     * @return
     */
    Page<TbSpecification> querySpecification(Page<TbSpecification> page,@Param("tbSpecificationVO") TbSpecificationDTO tbSpecificationDTO);
}
