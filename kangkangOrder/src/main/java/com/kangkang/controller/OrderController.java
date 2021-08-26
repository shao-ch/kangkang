package com.kangkang.controller;

import com.kangkang.service.OrderService;
import com.kangkang.store.viewObject.OrderVO;
import com.kangkang.tools.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName: OrderController   订单的controller
 * @Author: shaochunhai
 * @Date: 2021/8/26 2:15 下午
 * @Description: TODO
 */
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
            e.printStackTrace();
            return ResponseCode.message(500,null,"服务异常");
        }
    }


    /**
     * 查询订单初始化信息
     * @return
     */
    @GetMapping("/queryOrder")
    public ResponseCode<OrderVO> queryOrder(@RequestBody OrderVO order){

        try {
            //判定前端的数据没有问题
            if (order.getSkuId()==null){
                return ResponseCode.ok().body(null,"无商品信息");
            }else if (order.getTbAddress()==null){
                return ResponseCode.ok().body(null,"无地址信息");
            }else if (order.getUserId()==null){
                return ResponseCode.ok().body(null,"无人物信息");
            }

            //查询订单信息
            Map<String,Object> result= orderService.queryOrder(order);

            //返回数据
            if (result.get("error")==null){
                return ResponseCode.ok().body((OrderVO)result.get("order"));
            }
            return ResponseCode.ok().body(null,result.get("error").toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCode.message(500,null,"服务异常");
        }
    }

}
