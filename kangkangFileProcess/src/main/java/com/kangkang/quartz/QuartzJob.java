package com.kangkang.quartz;

/**
 * @ClassName: QuartzJob   定时器任务
 * @Author: shaochunhai
 * @Date: 2021/10/12 2:12 下午
 * @Description: TODO
 */
public interface QuartzJob {

    /**
     * 执行器
     */
    void excute();
}
