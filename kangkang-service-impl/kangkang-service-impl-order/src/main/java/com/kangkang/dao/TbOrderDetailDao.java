package com.kangkang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.store.entity.TbOrderDetail;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: TbOrderDetailDao  订单详情表的持久层
 * @Author: shaochunhai
 * @Date: 2021/8/29 4:52 下午
 * @Description: TODO
 */
@Repository
public interface TbOrderDetailDao extends BaseMapper<TbOrderDetail> {
}
