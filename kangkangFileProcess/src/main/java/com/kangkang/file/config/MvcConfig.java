package com.kangkang.file.config;

import com.kangkang.file.interceptor.RequestInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: MvcConfig  mvc的拦截器配置类
 * @Author: shaochunhai
 * @Date: 2021/8/21 3:39 下午
 * @Description: TODO
 */
//@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //将自定义的拦截器放入mvc中
        registry.addInterceptor(new RequestInterceptor());
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
