package com.kangkang;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @ClassName: OrderApplication  订单微服务
 * @Author: shaochunhai
 * @Date: 2021/8/26 2:53 下午
 * @Description: TODO
 */
@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.kangkang.dao")
public class OrderImplApplication {

    public static void main(String[] args) {

        log.info("OrderImplApplication 应用启动中。。。。");
        SpringApplication.run(OrderImplApplication.class,args);
    }
}
