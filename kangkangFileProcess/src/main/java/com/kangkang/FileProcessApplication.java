package com.kangkang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName: FileProcessApplication
 * @Author: shaochunhai
 * @Date: 2021/9/22 9:42 上午
 * @Description: TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.kangkang.file.dao")
public class FileProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileProcessApplication.class,args);
    }
}
