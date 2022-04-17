package com.kangkang.ERP.mynote;


import java.lang.annotation.*;

/**
 * 放在方法上面做一个标志，解析参数用的标志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamsAOP {

    /**
     * 需要解析的名称
     * @return
     */
    String parmsName() default "";

    /**
     * 需要解析的规则
     * @return
     */
    String rule() default "";

    /**
     * 默认值为String对象
     * @return
     */
    Class classz() default String.class;
}
