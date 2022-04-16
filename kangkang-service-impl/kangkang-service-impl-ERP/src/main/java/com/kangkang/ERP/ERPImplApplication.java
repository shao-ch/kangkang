package com.kangkang.ERP;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: ERPImplApplication
 * @Author: shaochunhai
 * @Date: 2021/8/7 3:34 下午
 * @Description: TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = {"com.kangkang.ERP.dao"})
public class ERPImplApplication {

    public static void main(String[] args) {

        SpringApplication.run(ERPImplApplication.class,args);
    }
}
