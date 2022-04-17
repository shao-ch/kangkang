package com.kangkang.ERP.interceptor;

import com.kangkang.ERP.mynote.ParamsAnalysis;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Parameter;

/**
 * @ClassName: ParamsHandleArgumentResolver  参数解析器
 * @Author: shaochunhai
 * @Date: 2022/4/17 12:58 上午
 * @Description: TODO
 */
//@Component
public class ParamsHandleArgumentResolver implements HandlerMethodArgumentResolver {
    /**
     * 如果含有这个注解就会解析参数
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(ParamsAnalysis.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        Parameter parameter = methodParameter.getParameter();

        System.out.println("parameter = " + parameter);

        return null;
    }
}
