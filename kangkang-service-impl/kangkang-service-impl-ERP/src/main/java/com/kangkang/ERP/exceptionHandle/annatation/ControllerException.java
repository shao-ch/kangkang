package com.kangkang.ERP.exceptionHandle.annatation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @AnnotationName: ControllerException  controller全局异常处理类
 * @Author: shaochunhai
 * @Date: 2022/4/20 10:39 下午
 * @Description: TODO
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerException {
}
