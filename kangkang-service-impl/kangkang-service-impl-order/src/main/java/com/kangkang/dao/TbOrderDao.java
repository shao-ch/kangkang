package com.kangkang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.store.entity.TbOrder;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: TbOrderDao  订单表的持久层
 * @Author: shaochunhai
 * @Date: 2021/8/27 9:19 上午
 * @Description: TODO
 */
@Repository
public interface TbOrderDao extends BaseMapper<TbOrder> {
}
