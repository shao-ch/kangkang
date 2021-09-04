package com.kangkang.listener;

import com.kangkang.enumInfo.RocketInfo;
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

        if (list.isEmpty()){
            log.info("没有消息需要处理");
            //直接返回成功
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        //获取消息
        MessageExt messageExt = list.get(0);
        //获取topic
        String topic = messageExt.getTopic();
        //获取tag
        String tags = messageExt.getTags();

        if (topic.equals(RocketInfo.SEND_LOG_TOPIC)&&tags.equals(RocketInfo.SEND_LOG_TAG)){
            //更新订单日志数据
            log.info("==========更新订单日志数据===========");
        }else if (topic.equals(RocketInfo.SEND_ORDER_TOPIC)&&tags.equals(RocketInfo.SEND_ORDER_TAG)){
            //更新库存数据
            log.info("==========更新库存数据===========");
        }


        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
