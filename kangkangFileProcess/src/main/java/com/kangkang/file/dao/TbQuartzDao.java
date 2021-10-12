package com.kangkang.file.dao;

import com.kangkang.file.utils.BatchBaseMapper;
import com.kangkang.manage.entity.TbQuartz;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName: TbQuartzDao  定时任务表
 * @Author: shaochunhai
 * @Date: 2021/10/12 4:13 下午
 * @Description: TODO
 */
@Repository
public interface TbQuartzDao extends BatchBaseMapper<TbQuartz> {
    List<TbQuartz> selectAll();
}
