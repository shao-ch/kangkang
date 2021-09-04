package com.kangkang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: KangkangOrderApplication
 * @Author: shaochunhai
 * @Date: 2021/9/4 1:22 下午
 * @Description: TODO
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class KangkangOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(KangkangOrderApplication.class,args);
    }
}
