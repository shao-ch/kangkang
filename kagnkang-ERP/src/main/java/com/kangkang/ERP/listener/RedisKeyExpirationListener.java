package com.kangkang.ERP.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @ClassName: RedisKeyExpirationListener
 * @Author: shaochunhai
 * @Date: 2021/8/18 5:42 下午
 * @Description: TODO
 */
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    private static final Logger log = LoggerFactory.getLogger(RedisKeyExpirationListener.class);
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }



    //这里可以拿到有哪些key过期了
    @Override
    public void onMessage(Message message, byte[] pattern) {

        String key = message.toString();

        log.info("该【"+key+"】已过期。");
    }
}
