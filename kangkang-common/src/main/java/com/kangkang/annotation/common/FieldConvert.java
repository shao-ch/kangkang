package com.kangkang.annotation.common;

import java.lang.annotation.*;

/**
 * @AnnotationName: FieldConvert
 * @Author: shaochunhai
 * @Date: 2022/4/22 4:47 下午
 * @Description: TODO
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldConvert {

    /**
     * 参数名称，这个要和后面的class类型的个数要相同，不然就会报错
     * @return
     */
    String[] parmName() default "";

    /**
     * 上面的参数类型
     * @return
     */
    Class[] paramsType() ;
}
