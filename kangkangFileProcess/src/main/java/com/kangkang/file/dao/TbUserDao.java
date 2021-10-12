package com.kangkang.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.manage.entity.TbUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName: TbUserDao
 * @Author: shaochunhai
 * @Date: 2021/10/12 2:20 下午
 * @Description: TODO
 */
@Repository
public interface TbUserDao extends BaseMapper<TbUser> {
    List<String> queryAllUser();
}
