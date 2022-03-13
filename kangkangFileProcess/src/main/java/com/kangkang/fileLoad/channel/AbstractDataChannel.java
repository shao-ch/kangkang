package com.kangkang.fileLoad.channel;

import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: AbstractDataChannel  数据抽象层  这里面定义了读和写的模版方法
 * @Author: shaochunhai
 * @Date: 2022/3/12 9:51 上午
 * @Description: TODO
 */
public abstract class AbstractDataChannel extends CacheChannel{

    /**
     * 队列容量
     */
    @Value("${queue.capacity}")
    private int capacity;

    @Value("${thread.number}")
    private int number;
    //管道的启动类
    @Override
    public void start() {
        init(capacity);
        read();
        write();
    }

    //管道停止的类
    @Override
    public void stop() {}

    //获取线程池
    protected ExecutorService getExecutor(){
        return Executors.newFixedThreadPool(number);
    }

    //读的模版方法
    protected void read(){};

    //写的模版方法
    protected void write(){};
}
