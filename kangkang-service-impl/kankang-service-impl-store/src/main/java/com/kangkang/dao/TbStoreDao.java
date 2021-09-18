package com.kangkang.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.store.entity.TbStore;
import com.kangkang.store.viewObject.TbStoreVO;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: StoreDao
 * @Author: shaochunhai
 * @Date: 2021/8/12 9:58 上午
 * @Description: TODO
 */
@Repository
public interface TbStoreDao extends BaseMapper<TbStore> {

    Page<TbStoreVO> selectStoreInfo(Page<TbStoreVO> page);
}
