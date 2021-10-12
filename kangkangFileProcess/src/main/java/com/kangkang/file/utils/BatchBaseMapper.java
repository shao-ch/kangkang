package com.kangkang.file.utils;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @InterfaceName: BatchBaseMapper  批量插入接口
 * @Author: shaochunhai
 * @Date: 2021/10/12 2:59 下午
 * @Description: TODO
 */
public interface BatchBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入
     * {@link com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn}
     *
     * @param entityList 要插入的数据
     * @return 成功插入的数据条数
     */
    int insertBatchSomeColumn(List<T> entityList);

}
