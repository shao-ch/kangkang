package com.kangkang.ERP.config;

import com.kangkang.ERP.interceptor.ParamsHandleArgumentResolver;
import com.kangkang.ERP.interceptor.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @ClassName: MvcConfig  mvc的拦截器配置类
 * @Author: shaochunhai
 * @Date: 2021/8/21 3:39 下午
 * @Description: TODO
 */
//@Slf4j
//@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private RequestInterceptor requestInterceptor;

//    @Autowired
//    private ParamsHandleArgumentResolver paramsHandleArgumentResolver;
//  自定义参数接拦截器
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//
//        log.info("============注册了参数解析器=============");
//        resolvers.add(paramsHandleArgumentResolver);
//    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        //将自定义的拦截器放入mvc中，这里是可以制定/interceptor下的拦截器的顺序，如果preHandle有一个是false，则就不往下执行了
//        registry.addInterceptor(requestInterceptor);
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }
}
