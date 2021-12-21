package com.kangkang.log.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName: EsProperties
 * @Author: shaochunhai
 * @Date: 2021/12/14 10:28 上午
 * @Description: TODO
 */
//@Data
//@ConfigurationProperties(prefix = "spring.elasticsearch")
public class EsProperties {

    /**
     * 连接超时时间
     */
    private int timeout;
    /**
     * 连接的ip和端口，可以是集群，集群之间用","隔开
     */
    private String node;


}
