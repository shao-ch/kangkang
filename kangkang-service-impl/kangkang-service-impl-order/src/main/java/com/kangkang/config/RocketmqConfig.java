package com.kangkang.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: RocketmqConfig   rocketmq的配置信息
 * @Author: shaochunhai
 * @Date: 2021/8/29 2:23 下午
 * @Description: TODO
 */
@Data
@Slf4j
@Configuration
public class RocketmqConfig {

    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;
    //生产者组
    @Value("${rocketmq.producer.groupName}")
    private String produceGroupName;

    // 生产者消息最大值
    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer produceMaxMessageSize;
    // 生产者消息发送超时时间
    @Value("${rocketmq.producer.sendMsgTimeOut}")
    private Integer produceSendMsgTimeOut;
    // 生产者失败重试次数
    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer produceRetryTimesWhenSendFailed;


    /**
     * mq的生产者配置
     *
     * @return
     * @throws MQClientException
     */
    @Bean
//    @ConditionalOnProperty(prefix = "rocketmq.producer", value = "isOnOff", havingValue = "on")
    @ConditionalOnProperty(prefix = "rocketmq.producer", value = "isOnOff")
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        log.info("defaultProducer 正在创建---------------------------------------");
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer(produceGroupName);
        //设置namesrv
        producer.setNamesrvAddr(namesrvAddr);
        //不走vip通道，因为vip通道的ip是固定的
        producer.setVipChannelEnabled(false);
        //设置消息最大字节数
        producer.setMaxMessageSize(produceMaxMessageSize);
        //消息的超时时间  默认3秒
        producer.setSendMsgTimeout(produceSendMsgTimeOut * 10);
        //消息失败之后的重试次数
        producer.setRetryTimesWhenSendAsyncFailed(produceRetryTimesWhenSendFailed);
        producer.start();
        log.info("rocketmq producer server 开启成功----------------------------------");
        return producer;
    }

}
