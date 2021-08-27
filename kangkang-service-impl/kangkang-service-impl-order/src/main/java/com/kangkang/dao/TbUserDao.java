package com.kangkang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.manage.entity.TbUser;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: TbUserDao
 * @Author: shaochunhai
 * @Date: 2021/8/27 10:58 上午
 * @Description: TODO
 */
@Repository
public interface TbUserDao extends BaseMapper<TbUser> {
}
