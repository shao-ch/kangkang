package com.kangkang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.store.entity.TbAfterSale;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName: TbAfterSaleDao
 * @Author: shaochunhai
 * @Date: 2021/9/11 4:45 下午
 * @Description: TODO
 */
@Repository
public interface TbAfterSaleDao extends BaseMapper<TbAfterSale> {
    List<TbAfterSale> selectListByCid(@Param("cid") Long cid);
}
