package com.kangkang.ERP.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.manage.entity.TbErpUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: TbErpUserDao
 * @Author: shaochunhai
 * @Date: 2022/2/28 1:42 下午
 * @Description: TODO
 */
@Repository
public interface TbErpUserDao extends BaseMapper<TbErpUser> {

    /**
     * 通过用户信息查询是否存在此用户
     * @param tbErpUser
     * @return
     */
    TbErpUser selectErpUser(@Param("tbErpUser") TbErpUser tbErpUser);

    TbErpUser selectErpByUserAndPassword(@Param("tbErpUser") TbErpUser tbErpUser);
}
