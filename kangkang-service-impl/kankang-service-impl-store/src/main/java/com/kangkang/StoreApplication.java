package com.kangkang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: StoreAppliation
 * @Author: shaochunhai
 * @Date: 2021/8/12 9:45 上午
 * @Description: TODO
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.kangkang.dao")
public class StoreApplication {

    public static void main(String[] args) {

        SpringApplication.run(StoreApplication.class,args);
    }
}
