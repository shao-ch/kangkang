package com.kangkang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.store.entity.TbShoppingCar;
import com.kangkang.store.viewObject.TbShoppingVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @InterfaceName: TbShoppingCarDao  购物车
 * @Author: shaochunhai
 * @Date: 2021/10/8 10:31 上午
 * @Description: TODO
 */
@Repository
public interface TbShoppingCarDao extends BaseMapper<TbShoppingCar> {
    List<Long> selectSkuIds(@Param("openId") String openId);

    List<TbShoppingVO> selectShoppingInfo(@Param("openId") String openId,@Param("ids")List<Long> ids);
}
