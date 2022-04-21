package com.kangkang.ERP.exceptionHandle.annatation;

import java.lang.annotation.*;

/**
 * @AnnotationName: KKExceptionHandler  异常处理类，放在方法上面的注解
 * @Author: shaochunhai
 * @Date: 2022/4/20 10:56 下午
 * @Description: TODO
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KKExceptionHandler {
}
