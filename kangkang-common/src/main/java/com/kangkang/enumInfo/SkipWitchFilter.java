package com.kangkang.enumInfo;

/**
 * @ClassName: SkipWitchFilter  指定跳过哪个过滤器
 * @Author: shaochunhai
 * @Date: 2021/9/9 11:20 上午
 * @Description: TODO
 */
public class SkipWitchFilter {
    /**
     * 用户鉴权token的拦截器
     */
    public static final String USER_TOKEN_FILTER="UserTokenFilter";


    /**
     * erp系统token的拦截器
     */
    public static final String ERP_TOKEN_FILTER="ERPTokenFilter";
    /**
     * 用户鉴权token的拦截器
     */
    public static final String GAGE_WAY_FILTER="GatewayFilter";

    /**
     * 过滤器初始化的key  0-代表走了路由过滤器，1-代表走了过滤器
     */
    public static final String INIT_FILTER="initFilter";
}
