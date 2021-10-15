package com.kangkang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.store.entity.TbOrder;
import com.kangkang.store.entity.TbShoppingCar;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.viewObject.OrderPageVO;
import com.kangkang.store.viewObject.OrderVO;
import com.kangkang.store.viewObject.OrderView;
import com.kangkang.store.viewObject.TbShoppingVO;
import com.kangkang.tools.ResultUtils;
import org.springframework.stereotype.Service;

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
    Map<String, Object> queryOrder(OrderVO order);

    /**
     * 生成订单
     * @param order
     */
    Map<String,Object> createOrder(OrderVO order);

    /**
     * 查询订单列表  0-代表查询全部订单，1-代付款订单，2-待收货订单，3-待评价订单
     * @param order
     * @return
     */
    Page<Map<String, Object>> queryOrderList(OrderPageVO order);

    /**
     * 查询代付款订单
     * @param orderPageVO
     * @return
     */
    Page<OrderView> queryPayingOrder(OrderPageVO orderPageVO);

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
    List<TbShoppingVO> queryShoppingCar(String openId,List<Long> ids);

    /**
     * 查询订单数量
     * @param openId
     * @return
     */
    ResultUtils<Map<String, Object>> queryOrderCount(String openId);
}
