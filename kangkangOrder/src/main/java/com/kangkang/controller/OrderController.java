package com.kangkang.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.service.OrderService;
import com.kangkang.store.viewObject.OrderPageVO;
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
    public ResponseCode<Map<String,Object>> createOrder(@RequestBody OrderVO orderVo){
        try {
            Map<String,Object> data = orderService.createOrder(orderVo);
            return ResponseCode.message(200,data,"success");
        } catch (Exception e) {
            log.error("=====生成订单服务异常=====：【"+ e+"】");
            return ResponseCode.message(500,null,"服务异常");
        }
    }


    /**
     * 查询全部订单列表
     * @return
     */
    @PostMapping("/queryOrderList")
    public ResponseCode<IPage<Map<String,Object>>> queryOrderList(@RequestBody OrderPageVO orderPageVO){

        log.info("=====查询全部订单列表======,接收参数为：【"+ JSONObject.toJSONString(orderPageVO)+"】");
        try {


            //查询全部订单列表
            Page<Map<String,Object>> result= orderService.queryOrderList(orderPageVO);

            return ResponseCode.ok().body(result,"success");
        } catch (Exception e) {
            log.error("=====查询全部订单列表服务异常=====：【"+ e+"】");
            return ResponseCode.message(500,null,"服务异常");
        }
    }




}
