package com.kangkang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: KangkangStoreApplication
 * @Author: shaochunhai
 * @Date: 2021/8/11 11:41 上午
 * @Description: TODO
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class KangkangStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(KangkangStoreApplication.class,args);
    }
}

