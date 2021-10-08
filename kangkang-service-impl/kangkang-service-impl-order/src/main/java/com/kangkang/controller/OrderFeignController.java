package com.kangkang.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.service.OrderFeignService;
import com.kangkang.service.OrderService;
import com.kangkang.store.entity.TbOrder;
import com.kangkang.store.entity.TbShoppingCar;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.viewObject.OrderPageVO;
import com.kangkang.store.viewObject.OrderVO;
import com.kangkang.store.viewObject.OrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: OrderFeignController
 * @Author: shaochunhai
 * @Date: 2021/8/27 9:16 上午
 * @Description: TODO
 */
@RestController
public class OrderFeignController implements OrderFeignService {

    @Autowired
    private OrderService orderService;

    /**
     * 查询订单初始化信息
     *
     * @param order
     * @return
     */
    @Override
    public Map<String, Object> queryOrder(OrderVO order) {
        return orderService.queryOrder(order);
    }

    /**
     * 生成订单
     *
     * @param order
     */
    @Override
    public Map<String,Object> createOrder(OrderVO order) {
       return orderService.createOrder(order);
    }

    /**
     * 查询订单列表  0-代表查询全部订单，1-代付款订单，2-待收货订单，3-待评价订单
     * @param order
     * @return
     */
    @Override
    public Page<Map<String, Object>> queryOrderList(OrderPageVO order) {
        return orderService.queryOrderList(order);
    }


    /**
     * 查询代付款订单
     * @param orderPageVO
     * @return
     */
    @Override
    public Page<OrderView> queryPayingOrder(OrderPageVO orderPageVO) {
        return orderService.queryPayingOrder(orderPageVO);
    }


    /**
     * 添加购物车
     * @param shoppingCar
     */
    @Override
    public void addShoppingCar(TbShoppingCar shoppingCar) {
        orderService.addShoppingCar(shoppingCar);
    }


    /**
     * 查询购物车数量
     * @param openId
     * @return
     */
    @Override
    public Integer getShopCarCount(String openId) {
        return orderService.getShopCarCount(openId);
    }

    /**
     * 删除购物车信息
     * @param cars
     * @return
     */
    @Override
    public void deleteShoppingCar(List<Long> cars) {
        orderService.deleteShoppingCar(cars);
    }


    /**
     * 查询购物车内容
     * @param openId
     * @return
     */
    @Override
    public List<TbSku> queryShoppingCar(String openId) {
        return orderService.queryShoppingCar(openId);
    }
}
