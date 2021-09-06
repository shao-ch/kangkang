package com.kangkang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.store.entity.TbStock;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: TbStockManageDao
 * @Author: shaochunhai
 * @Date: 2021/9/6 3:43 下午
 * @Description: TODO
 */
@Repository
public interface TbStockManageDao extends BaseMapper<TbStock> {
    void updateStockBySkuId(Long valueOf);
}
