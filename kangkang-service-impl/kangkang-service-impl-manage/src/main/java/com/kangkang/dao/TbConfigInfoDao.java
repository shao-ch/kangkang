package com.kangkang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.manage.entity.TbConfInfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @InterfaceName: TbConfigInfoDao
 * @Author: shaochunhai
 * @Date: 2021/9/22 10:54 上午
 * @Description: TODO
 */
@Repository
public interface TbConfigInfoDao extends BaseMapper<TbConfInfo> {

    /**
     * 查询所有配置信息
     * @return
     */
    List<Map<String,String>> queryAllConfig();
}
