package com.kangkang.dao;

import com.kangkang.manage.entity.TbAddress;
import com.kangkang.manage.viewObject.TbAdressVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName: TbAddressDao  这个是纯mybatis写
 * @Author: shaochunhai
 * @Date: 2021/8/21 4:56 下午
 * @Description: TODO
 */
@Repository
public interface TbAddressDao {


    List<TbAddress> selectAddress(@Param("id") Integer id);
}
