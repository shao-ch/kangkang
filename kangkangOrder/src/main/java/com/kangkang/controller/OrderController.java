package com.kangkang.controller;

import com.alibaba.fastjson.JSONObject;
import com.kangkang.service.OrderService;
import com.kangkang.store.viewObject.OrderVO;
import com.kangkang.tools.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName: OrderController   订单的controller
 * @Author: shaochunhai
 * @Date: 2021/8/26 2:15 下午
 * @Description: TODO
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;



    /**
     * 生成订单
     * @return
     */
    @PostMapping("/createOrder")
    public ResponseCode<Void> createOrder(@RequestBody OrderVO order){
        try {
            orderService.createOrder(order);
            return ResponseCode.message(200,null,"success");
        } catch (Exception e) {
            log.error("=====生成订单服务异常=====：【"+ e+"】");
            return ResponseCode.message(500,null,"服务异常");
        }
    }


    /**
     * 查询订单初始化信息
     * @return
     */
    @GetMapping("/queryOrder")
    public ResponseCode<OrderVO> queryOrder(@RequestBody OrderVO order){

        log.info("=====查询订单初始化信息=====,接收参数为：【"+ JSONObject.toJSONString(order)+"】");
        try {
            //判定前端的数据没有问题
            if (order.getSkuIds().isEmpty()||order.getSkuIds()==null){
                return ResponseCode.ok().body(null,"无商品信息");
            }else if (order.getTbAddress()==null){
                return ResponseCode.ok().body(null,"无地址信息");
            }else if (order.getUserId()==null){
                return ResponseCode.ok().body(null,"无人物信息");
            }

            //查询订单信息
            Map<String,Object> result= orderService.queryOrder(order);
            log.info("=====查询订单的查询结果为=====：【"+ result+"】");
            //返回数据
            if (result.get("error")==null){
                return ResponseCode.ok().body((OrderVO)result.get("order"));
            }
            return ResponseCode.ok().body(null,result.get("error").toString());
        } catch (Exception e) {
            log.error("=====查询订单的服务异常=====：【"+ e+"】");
            return ResponseCode.message(500,null,"服务异常");
        }
    }

}
