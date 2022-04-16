package com.kangkang.ERP.config;

import com.kangkang.ERP.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //将自定义的拦截器放入mvc中，这里是可以制定/interceptor下的拦截器的顺序，如果preHandle有一个是false，则就不往下执行了
        registry.addInterceptor(requestInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
