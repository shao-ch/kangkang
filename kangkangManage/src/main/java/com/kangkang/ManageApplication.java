package com.kangkang;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: com.kangkang.ManageApplication
 * @Author: shaochunhai
 * @Date: 2021/8/5 2:06 下午
 * @Description: TODO
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients   //这个是启动feign客户端，做负载
public class ManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class,args);
    }
}
