package com.kangkang.tools;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @ClassName: BeanUtils
 * @Author: shaochunhai
 * @Date: 2021/12/29 5:40 下午
 * @Description: TODO
 */
public class KangkangBeanUtils {


    public static <T> T mapToBean(Map<String,Object> map,Class<T> clasz) {

        String s = JSONObject.toJSONString(map);

        T t = JSONObject.parseObject(s, clasz);
        return t;
    }
}
