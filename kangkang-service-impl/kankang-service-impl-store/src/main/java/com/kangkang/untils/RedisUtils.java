package com.kangkang.untils;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @ClassName: RedisUtils   redis工具类
 * @Author: shaochunhai
 * @Date: 2021/8/30 3:28 下午
 * @Description: TODO
 */
public class RedisUtils {


    /**
     * 自减，这是原子操作
     * @param redisTemplate
     * @return
     */
    public static boolean decrement(RedisTemplate<String,Object> redisTemplate,String key){
        //判断key纯不存在
        Object value = redisTemplate.opsForValue().get(key);
        if (value==null){
            return false;
        }
        //获取原子操作类
        redisTemplate.opsForValue().decrement(key);
        return true;
    }

    /**
     * 增加库存
     * @param redisTemplate
     * @param key
     */
    public static void increment(RedisTemplate<String, Object> redisTemplate, String key) {
        //获取原子操作类
        redisTemplate.opsForValue().increment(key);
    }
}
