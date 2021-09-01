package com.kangkang.enumInfo;

/**
 * @ClassName: RocketInfo   rocket的枚举数据
 * @Author: shaochunhai
 * @Date: 2021/9/1 4:27 下午
 * @Description: TODO
 */
public class RocketInfo {


    /**
     * 发送日志的topic
     */
    public static final String SEND_LOG_TOPIC="log_topic";

    /**
     * 发送订单的topic
     */
    public static final String SEND_ORDER_TOPIC="order_topic";

    /**
     * manage消费者组   按照服务来区分消费者组
     */
    public static final String MANAGE_GROUP="manage_consumer_group";
}
