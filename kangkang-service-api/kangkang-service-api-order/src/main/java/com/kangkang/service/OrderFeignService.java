package com.kangkang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.store.entity.TbShoppingCar;
import com.kangkang.store.dtoObject.OrderPageDTO;
import com.kangkang.store.dtoObject.OrderDTO;
import com.kangkang.store.dtoObject.OrderView;
import com.kangkang.store.dtoObject.TbShoppingDTO;
import com.kangkang.tools.ResultUtils;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

/**
 * @InterfaceName: OrderFeignService
 * @Author: shaochunhai
 * @Date: 2021/8/26 2:56 下午
 * @Description: TODO
 */
public interface OrderFeignService {

    /**
     * 查询订单初始化信息
     * @param order
     * @return
     */
    @LoadBalanced
    @GetMapping(value = "order/queryOrder")
    Map<String, Object> queryOrder(@RequestBody OrderDTO order);


    @LoadBalanced
    @PostMapping(value = "order/createOrder")
    Map<String,Object> createOrder(@RequestBody OrderDTO order);

    /**
     * 查询订单列表  0-代表查询全部订单，1-代付款订单，2-待收货订单，3-待评价订单
     * @param order
     * @return
     */
    @LoadBalanced
    @PostMapping(value = "order/queryOrderList")
    Page<Map<String, Object>> queryOrderList(@RequestBody OrderPageDTO order);

    /**
     * 查询代付款订单
     * @param orderPageDTO
     * @return
     */
    @LoadBalanced
    @PostMapping(value = "order/queryPayingOrder")
    Page<OrderView> queryPayingOrder(@RequestBody OrderPageDTO orderPageDTO);
    /**
     * 查添加购物车
     * @param shoppingCar
     * @return
     */
    @LoadBalanced
    @PostMapping(value = "order/addShoppingCar")
    void addShoppingCar(@RequestBody TbShoppingCar shoppingCar);

    /**
     * 查询购物车数量
     * @param openId
     * @return
     */
    @LoadBalanced
    @GetMapping(value = "order/getShopCarCount")
    Integer getShopCarCount(@RequestParam("openId")String openId);

    /**
     * 删除购物车信息
     * @param openid
     * @param cars
     * @return
     */
    @LoadBalanced
    @DeleteMapping(value = "order/deleteShoppingCar")
    void deleteShoppingCar(@RequestParam("cars")List<Long> cars);


    /**
     * 查询购物车内容
     * @param openId
     * @return
     */
    @LoadBalanced
    @GetMapping(value = "order/queryShoppingCar")
    List<TbShoppingDTO> queryShoppingCar(@RequestParam("openId") String openId, @RequestParam("ids")List<Long> ids);


    /**
     * 查询订单数量
     * @param openId
     * @return
     */
    @LoadBalanced
    @GetMapping(value = "order/queryOrderCount")
    ResultUtils<Map<String, Object>> queryOrderCount(@RequestParam("openId") String openId);
}
