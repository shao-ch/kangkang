package com.kangkang.ERP.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: RocketmqMessageProcessor   消息处理器
 * @Author: shaochunhai
 * @Date: 2021/9/1 5:24 下午
 * @Description: TODO
 */
@Slf4j
@Component
public class RocketmqMessageProcessor implements MessageListenerConcurrently {


    /**
     * 因为之前设置的setConsumeMessageBatchMaxSize 为1  所以list里面只有一条数据
     * @param list
     * @param consumeConcurrentlyContext
     * @return
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {



        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
