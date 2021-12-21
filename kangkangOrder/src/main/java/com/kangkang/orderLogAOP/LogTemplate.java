package com.kangkang.orderLogAOP;

import lombok.Data;

/**
 * @ClassName: LogTemplate  发送的日志收集模版类，和logback中logstash对应的一致
 * @Author: shaochunhai
 * @Date: 2021/12/6 5:21 下午
 * @Description: TODO
 */
@Data
public class LogTemplate {
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    private String requestRawJson;
    private String responseRawJson;
    /**
     * 响应时间
     */
    private Long responseTime;
    /**
     * url链接
     */
    private String url;
    /**
     * 方法名称
     */
    private String method;
    /**
     * 路径
     */
    private String path;

    private String message;
}
