package com.kangkang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.manage.entity.TbUser;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: KangKangUserDao
 * @Author: shaochunhai
 * @Date: 2021/8/8 4:01 下午
 * @Description: TODO
 */
@Repository
public interface KangKangUserDao extends BaseMapper<TbUser> {
}
