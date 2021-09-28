package com.kangkang.file.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @ClassName: RequestInterceptor  请求的拦截器，所有的请求都要先过拦截器
 * @Author: shaochunhai
 * @Date: 2021/8/21 3:36 下午
 * @Description: TODO
 */

public class RequestInterceptor implements HandlerInterceptor {


    /**
     * 这里使用的是前置处理器
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        //获取请求是否携带token，如果不携带就直接拒绝
        String token = request.getHeader("token");

        if (token==null){
            //禁止操作
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            HashMap<String, Object> map = new HashMap<>();
            map.put("code",403);
            map.put("data","拒绝操作");
            response.getWriter().print(JSONObject.toJSON(map));

            return false;
        }
        return true;
    }
}
