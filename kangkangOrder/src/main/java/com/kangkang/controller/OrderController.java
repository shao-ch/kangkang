package com.kangkang.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.service.OrderService;
import com.kangkang.store.entity.TbShoppingCar;
import com.kangkang.store.dtoObject.OrderPageDTO;
import com.kangkang.store.dtoObject.OrderDTO;
import com.kangkang.store.dtoObject.OrderView;
import com.kangkang.store.dtoObject.TbShoppingDTO;
import com.kangkang.tools.ResponseCode;
import com.kangkang.tools.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: OrderController   订单的controller
 * @Author: shaochunhai
 * @Date: 2021/8/26 2:15 下午
 * @Description: TODO
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 生成订单
     * @return
     */
    @PostMapping("/createOrder")
    public ResponseCode<Map<String,Object>> createOrder(@RequestBody OrderDTO orderDTO){
        try {
            Map<String,Object> data = orderService.createOrder(orderDTO);
            if (data.get("status").equals("success")){
                return ResponseCode.message(200,data,"success");
            }else {
                return ResponseCode.message(500,data,"创建失败");
            }

        } catch (Exception e) {
            log.error("=====生成订单服务异常=====：【"+ e+"】");
            return ResponseCode.message(500,null,"服务异常");
        }
    }


    /**
     * 查询订单列表  0-代表查询全部订单，1-代付款订单，2-待收货订单，3-待评价订单
     * @param orderPageDTO
     * @return
     */
    @PostMapping("/queryOrderList")
    public ResponseCode<IPage<Map<String,Object>>> queryOrderList(@RequestBody OrderPageDTO orderPageDTO){

        log.info("=====查询订单列表======,接收参数为：【"+ JSONObject.toJSONString(orderPageDTO)+"】");
        try {


            //查询全部订单列表
            Page<Map<String,Object>> result= orderService.queryOrderList(orderPageDTO);

            return ResponseCode.ok().body(result,"success");
        } catch (Exception e) {
            log.error("=====查询订单列表服务异常=====：【"+ e+"】");
            return ResponseCode.message(500,null,"服务异常");
        }
    }


    /**
     * 查询代付款，已付款，代发货等信息订单
     * @param orderPageDTO
     * @return
     */
    @PostMapping("/queryPayingOrder")
    public ResponseCode<IPage<OrderView>> queryPayingOrder(@RequestBody OrderPageDTO orderPageDTO){

        log.info("=====查询代付款订单======,接收参数为：【"+ JSONObject.toJSONString(orderPageDTO)+"】");
        try {


            //查询代付款订单
            Page<OrderView> result= orderService.queryPayingOrder(orderPageDTO);

            return ResponseCode.ok().body(result,"success");
        } catch (Exception e) {
            log.error("=====查询代付款订单服务异常=====：【"+ e+"】");
            return ResponseCode.message(500,null,"服务异常");
        }
    }

    /**
     * 添加购物车
     * @param shoppingCar
     * @return
     */
    @PostMapping("/addShoppingCar")
    public ResponseCode<IPage<OrderView>> addShoppingCar(@RequestBody TbShoppingCar shoppingCar){

        log.info("=====添加购物车======,接收参数为：【"+ JSONObject.toJSONString(shoppingCar)+"】");
        try {
            //添加购物车
           orderService.addShoppingCar(shoppingCar);

            return ResponseCode.ok().body(null,"success");
        } catch (Exception e) {
            log.error("=====添加购物车服务异常=====：【"+ e+"】");
            return ResponseCode.message(500,null,"服务异常");
        }
    }

    /**
     * 查询购物车数量
     * @param openId
     * @return
     */
    @GetMapping("/getShopCarCount")
    public ResponseCode<Integer> addShoppingCar(@RequestParam("openid") String openId){

        log.info("=====查询购物车数量======,接收参数为：【"+ JSONObject.toJSONString(openId)+"】");
        try {
            //添加购物车
            Integer count=orderService.getShopCarCount(openId);

            return ResponseCode.ok().body(count,"success");
        } catch (Exception e) {
            log.error("=====查询购物车数量服务异常=====：【"+ e+"】");
            return ResponseCode.message(500,null,"服务异常");
        }
    }


    /**
     * 删除购物车信息
     * @param json
     * @return
     */
    @PostMapping("/deleteShoppingCar")
    public ResponseCode<String> deleteShoppingCar(@RequestBody String json){

        log.info("=====删除购物车信息======,接收参数为：【"+ JSONObject.toJSONString(json)+"】");
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            List<Long> cars = (List<Long>) jsonObject.get("cars");
            if (cars.isEmpty()){
                return ResponseCode.message(500,null,"无商品信息需要删除");
            }
            //添加购物车
            orderService.deleteShoppingCar(cars);

            return ResponseCode.ok().body("删除成功","success");
        } catch (Exception e) {
            log.error("=====查询购物车数量服务异常=====：【"+ e+"】");
            return ResponseCode.message(500,null,"服务异常");
        }
    }




    /**
     * 查询购物车内容
     * @param json
     * @return
     */
    @ResponseBody
    @PostMapping("/queryShoppingCar")
    public ResponseCode<List<TbShoppingDTO>> queryShoppingCar(@RequestBody String json){

        log.info("=====查询购物车内容======,接收参数为：【"+ JSONObject.toJSONString(json)+"】");
        List<Long> ids=new ArrayList<>();
        try {
            //添加购物车
            JSONObject jsonObject = JSONObject.parseObject(json);
            String openId = (String) jsonObject.get("openId");
            List<String> list = (List<String>) jsonObject.get("id");
            if (!list.isEmpty()){
                for (String s : list) {
                    ids.add(Long.valueOf(s));
                }
            }
            List<TbShoppingDTO> result=orderService.queryShoppingCar(openId,ids);

            return ResponseCode.ok().body(result,"success");
        } catch (Exception e) {
            log.error("=====查询购物车数量服务异常=====：【"+ e+"】");
            return ResponseCode.message(500,null,"服务异常");
        }
    }



    /**
     * 查询订单数量
     * @param openId
     * @return
     */
    @PostMapping("/queryOrderCount")
    public ResponseCode<Map<String,Object>> queryOrderCount(@RequestParam("openid") String openId){

        log.info("=====查询订单数量======,接收参数为：【"+ JSONObject.toJSONString(openId)+"】");
        try {
            //查询订单数量
            ResultUtils<Map<String,Object>> result=orderService.queryOrderCount(openId);

            if (result.getFlag().equals("0")){
                return ResponseCode.ok().body(result.getData(),"success");
            }
           return ResponseCode.message(500,null,result.getError());
        } catch (Exception e) {
            log.error("=====查询订单数量=====：【"+ e+"】");
            return ResponseCode.message(500,null,"服务异常");
        }
    }
}
