package com.kangkang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.manage.entity.TbArea;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName: TbAreaDao    plus 和mybatis共存
 * @Author: shaochunhai
 * @Date: 2021/8/21 5:08 下午
 * @Description: TODO
 */
@Repository
public interface TbAreaDao extends BaseMapper<TbArea> {
    /**
     * 及联查询所有具体地址
     * @param id
     * @return
     */
    List<TbArea> selectAllLevel(@Param("id") long id);
}
