package com.kangkang.enumInfo;

/**
 * @ClassName: RedisKeyPrefix  redis前缀
 * @Author: shaochunhai
 * @Date: 2021/8/30 4:06 下午
 * @Description: TODO
 */
public class RedisKeyPrefix {

    /**
     * 扣减库存在redis中的key
     */
    public static final String STOCK_PREFIX_KEY="redis_stock_key_";

    /**
     * 扣减库存的分布式锁
     */
    public static final String STOCK_LOCK_KEY="stock_lock_key_";
}
