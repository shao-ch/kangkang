package com.kangkang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @ClassName: ESLogApplication
 * @Author: shaochunhai
 * @Date: 2021/12/7 8:50 下午
 * @Description: TODO
 * EnableConfigurationProperties 使使用 @ConfigurationProperties 注解的类生效。没有EnableConfigurationProperties
 * 这个注解，ConfigurationProperties这个注解就会报错
 */

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class ESLogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ESLogApplication.class,args);
        log.info("========success for ESLogApplication========");
    }
}
