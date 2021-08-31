package com.kangkang.untils;

import com.kangkang.RedisKeyPrefix;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;

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
        RedisAtomicInteger integer = new RedisAtomicInteger(key, redisTemplate.getConnectionFactory());
        integer.getAndDecrement();
        return true;
    }

    /**
     * 生成库存数据
     * @param redisTemplate
     * @param stock
     * @param key   锁的key
     */
    public static void generateRedis(RedisTemplate<String, Object> redisTemplate, Integer stock, String key) {
        //获取分布式锁信息
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(key, 0);
        //如果获取锁成功就去redis生成库存数据
        if (lock){

        }else {
            //如果没有成功，说明已经存在库存，就去扣减库存
        }


    }

}
