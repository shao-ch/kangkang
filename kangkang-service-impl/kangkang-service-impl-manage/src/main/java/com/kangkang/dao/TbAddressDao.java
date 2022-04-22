package com.kangkang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.manage.entity.TbAddress;
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
public interface TbAddressDao extends BaseMapper<TbAddress> {

    List<TbAddress> selectAddress(@Param("openId") String openId);

    void updateIsDefaultByUserId(@Param("openId")String openId,@Param("isDefault") char isDefault);

}
