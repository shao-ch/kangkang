package com.kangkang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.store.entity.TbShoppingCar;
import com.kangkang.store.dtoObject.OrderPageDTO;
import com.kangkang.store.dtoObject.OrderDTO;
import com.kangkang.store.dtoObject.OrderView;
import com.kangkang.store.dtoObject.TbShoppingDTO;
import com.kangkang.tools.ResultUtils;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName: OrderService  订单服务
 * @Author: shaochunhai
 * @Date: 2021/8/27 9:18 上午
 * @Description: TODO
 */

public interface OrderService {

    /**
     * 查询订单初始化信息
     * @param order
     * @return
     */
    Map<String, Object> queryOrder(OrderDTO order);

    /**
     * 生成订单
     * @param order
     */
    Map<String,Object> createOrder(OrderDTO order);

    /**
     * 查询订单列表  0-代表查询全部订单，1-代付款订单，2-待收货订单，3-待评价订单
     * @param order
     * @return
     */
    Page<Map<String, Object>> queryOrderList(OrderPageDTO order);

    /**
     * 查询代付款订单
     * @param orderPageDTO
     * @return
     */
    Page<OrderView> queryPayingOrder(OrderPageDTO orderPageDTO);

    /**
     * 添加购物车
     * @param shoppingCar
     */
    void addShoppingCar(TbShoppingCar shoppingCar);

    /**
     * 查询购物车数量
     * @param openId
     * @return
     */
    Integer getShopCarCount(String openId);

    /**
     * 删除购物车信息
     * @param cars
     * @return
     */
    void deleteShoppingCar(List<Long> cars);

    /**
     * 查询购物车内容
     * @param openId
     * @param ids
     * @return
     */
    List<TbShoppingDTO> queryShoppingCar(String openId, List<Long> ids);

    /**
     * 查询订单数量
     * @param openId
     * @return
     */
    ResultUtils<Map<String, Object>> queryOrderCount(String openId);
}
