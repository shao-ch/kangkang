package com.kangkang.config;

import com.kangkang.enumInfo.RocketInfo;
import com.kangkang.listener.RocketmqMessageProcessor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: RocketmqConsumerConfig  rocketmq的配置
 * @Author: shaochunhai
 * @Date: 2021/9/1 4:53 下午
 * @Description: TODO
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "rocketmq.consumer")
public class RocketmqConsumerConfig {

    private String namesrvAddr;
    private String isOnOff;
    private String groupName;
    private Integer consumeThreadMin;
    private Integer consumeThreadMax;
    private Integer consumeMessageBatchMaxSize;

    @Autowired
    private RocketmqMessageProcessor rocketmqMessageProcessor;

    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer() throws MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        //设置地址
        consumer.setNamesrvAddr(namesrvAddr);
        //设置最小线程数
        consumer.setConsumeThreadMin(consumeThreadMin);
        //设置最大线程数
        consumer.setConsumeThreadMax(consumeThreadMax);
        //设置批量消费的大小，就是消费者一次性消费的消息数  和pullBatchSize 有区别，pullBatchSize是从broker拉取的最大消息数
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);

        //设置消费者监听
        consumer.registerMessageListener(rocketmqMessageProcessor);
        /**
         * 表示第一次启动的时候从哪里开始，我设置的从尾部开始，如果不是第一次启动，从上一次消费的位置开始消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        /**
         * 设置消费模式   负载和广播。默认负载   下面设置的就是负载模式
         */
        consumer.setMessageModel(MessageModel.CLUSTERING);

        try {
            /**
             * 设置消费者订阅消息
             */
            consumer.subscribe(RocketInfo.SEND_LOG_TOPIC,RocketInfo.SEND_LOG_TAG);
            consumer.subscribe(RocketInfo.SEND_ORDER_TOPIC,RocketInfo.SEND_ORDER_TAG);

            //启动消费者
            consumer.start();

            log.info("consumer 创建成功 groupName={},  namesrvAddr={}",groupName,namesrvAddr);
        } catch (MQClientException e) {
            log.error("consumer 创建失败!===失败原因："+e);
        }

        return consumer;
    }
}
