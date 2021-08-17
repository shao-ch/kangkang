package sch.kangkang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: KangkangGatewayApplication
 * @Author: shaochunhai
 * @Date: 2021/8/16 9:55 上午
 * @Description: TODO
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class KangkangGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(KangkangGatewayApplication.class,args);
    }
}
