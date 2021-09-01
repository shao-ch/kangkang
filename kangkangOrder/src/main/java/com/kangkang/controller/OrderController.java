package com.kangkang.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kangkang.service.OrderService;
import com.kangkang.store.entity.TbOrder;
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
    public ResponseCode<TbOrder> createOrder(@RequestBody OrderVO orderVo){
        try {
            TbOrder order = orderService.createOrder(orderVo);
            return ResponseCode.message(200,order,"success");
        } catch (Exception e) {
            log.error("=====生成订单服务异常=====：【"+ e+"】");
            return ResponseCode.message(500,null,"服务异常");
        }
    }


    /**
     * 查询全部订单列表
     * @return
     */
    @GetMapping("/queryOrderList")
    public ResponseCode<IPage<Map<String,Object>>> queryOrderList(@RequestBody OrderVO order){

        log.info("=====查询全部订单列表======,接收参数为：【"+ JSONObject.toJSONString(order)+"】");
        try {


            //查询全部订单列表
            IPage<Map<String,Object>> result= orderService.queryOrderList(order);

            return ResponseCode.ok().body(result,"success");
        } catch (Exception e) {
            log.error("=====查询全部订单列表服务异常=====：【"+ e+"】");
            return ResponseCode.message(500,null,"服务异常");
        }
    }



}
