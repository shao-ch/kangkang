package sch.kangkang.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import sch.kangkang.listener.RedisKeyExpirationListener;

/**
 * @ClassName: RedisConfi
 * @Author: shaochunhai
 * @Date: 2021/8/18 5:37 下午
 * @Description: TODO
 */
@Configuration
public class RedisConfig {

    //注入redisTemple<string,object>模版，然后配置效率相对较高的序列化
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        //创建redis模版
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //将连接工厂给模版
        redisTemplate.setConnectionFactory(factory);
        //使用Jackson2JsonRedisSerialize替换默认的JDK序列化
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper mapper = new ObjectMapper();

        //这里设置类中属性的全不权限，不管是有没有get或者set方法的都可以遵循后面的规则，反序列化后可以获取驼峰名称的属性
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        //配置序列化时候指定序列化的类型存入redis，不配置这个在反序列化的时候都是只有map，要自己转
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        //指定序列化的规则
        serializer.setObjectMapper(mapper);

        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);

        return redisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        //这里是监听0索引库下key过期的数据
        container.addMessageListener(new RedisKeyExpirationListener(container), new PatternTopic("__keyevent@0__:expired"));
        return container;
    }
}
