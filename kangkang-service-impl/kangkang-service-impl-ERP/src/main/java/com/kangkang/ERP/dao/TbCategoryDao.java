package com.kangkang.ERP.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.manage.entity.TbCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName: TbCategoryDao
 * @Author: shaochunhai
 * @Date: 2021/9/8 3:53 下午
 * @Description: TODO
 */
@Repository
public interface TbCategoryDao extends BaseMapper<TbCategory> {
    List<TbCategory> queryCategoryInfoByParendId(@Param("parentId") Long parentId);
}
