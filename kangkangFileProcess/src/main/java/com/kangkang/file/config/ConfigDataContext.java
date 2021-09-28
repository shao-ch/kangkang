package com.kangkang.file.config;

import java.util.HashMap;

/**
 * @ClassName: ConfigDataContext  配置类容器
 * @Author: shaochunhai
 * @Date: 2021/9/22 10:39 上午
 * @Description: TODO
 */
public class ConfigDataContext {
    public static HashMap<String,String> configData=new HashMap<>();


    /**
     * 获取配置的value
     * @param key
     * @return
     */
    public static String getValue(String key){
        String s = configData.get(key);
        return s;
    }
}
