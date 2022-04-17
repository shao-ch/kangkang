package com.kangkang.ERP.mynote;

import java.lang.annotation.*;

/**
 * @interface: ParamsAnalysis  参数解析的注解
 * @Author: shaochunhai
 * @Date: 2021/8/7 3:34 下午
 * @Description: TODO
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamsAnalysis {

    /**
     * 你要解析的参数名称
     * @return
     */
    String paramName() default "";
}
