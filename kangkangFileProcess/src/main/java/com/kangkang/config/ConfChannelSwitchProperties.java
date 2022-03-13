package com.kangkang.config;

import com.kangkang.fileLoad.start.Starter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName: ConfProProperties
 * @Author: shaochunhai
 * @Date: 2022/3/12 10:22 上午
 * @Description: TODO
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "channel")
public class ConfChannelSwitchProperties {


    private Map<String,String> switchs;

    @Bean
    public Starter starter(){
        return new Starter();
    }
}
