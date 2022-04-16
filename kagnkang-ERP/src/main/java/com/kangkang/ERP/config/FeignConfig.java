package com.kangkang.ERP.config;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: ManageConfig  //配置调用方的负载均衡算法，也就是ribbon的负载均衡的策略
 * @Author: shaochunhai
 * @Date: 2021/8/5 2:53 下午
 * @Description: TODO
 */
@Configuration
public class FeignConfig {

//    @LoadBalanced
//    @Bean
//    public RestTemplate restTemplate() {
//
//        return new RestTemplate();
//    }

    /**配置负载均衡算法
     * return new RoundRobinRule();轮询策略
     *
     * return new RandomRule();随机策略
     *
     * return new AvailabilityFilteringRule();首先会过滤掉故障机或者并发链接数超过阈值的服务器.剩余的机器轮询配置
     *
     * return new WeightedResponseTimeRule();服务器影响时间越快,则权重越高
     *
     * return new BestAvailableRule();最大可用策略，即先过滤出故障服务器后，选择一个当前并发请求数最小的
     *
     *
     * @return
     */
    @Bean
    public IRule myRule(){
       return new AvailabilityFilteringRule();
    }


    /**
     * 这是feign的重试配置，不推荐这样使用，推荐使用配置文件的形式
     * @return
     */
//    @Bean
//    public Retryer myRetry(){
//      return new Retryer.Default();
//    }
}
