package com.kangkang.untils;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName: MqUtils   消息队列的工具类
 * @Author: shaochunhai
 * @Date: 2021/9/1 4:20 下午
 * @Description: TODO
 */
public class MqUtils {


    /**
     * 发送消息
     * @param producer
     * @param topic
     * @param tag
     * @param msg
     * @return
     * @throws MQBrokerException
     * @throws RemotingException
     * @throws InterruptedException
     * @throws MQClientException
     */
    public static SendResult send (DefaultMQProducer producer,String topic,String tag,String msg) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {

        Message message = new Message(topic, tag, msg.getBytes(StandardCharsets.UTF_8));
        return producer.send(message);
    }
}
