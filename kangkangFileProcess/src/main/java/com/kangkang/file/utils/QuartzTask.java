package com.kangkang.file.utils;

import com.kangkang.quartz.QuartzJob;

/**
 * @ClassName: QuartzTask
 * @Author: shaochunhai
 * @Date: 2021/10/12 4:34 下午
 * @Description: TODO
 */
public class QuartzTask implements Runnable{

    private QuartzJob quartzJob;

    private QuartzTask(QuartzJob quartzJob) {
        this.quartzJob = quartzJob;
    }

    /**
     * 任务执行的类
     */
    @Override
    public void run() {
        quartzJob.excute();
    }

    /**
     * 单例模式
     * @param quartzJob
     * @return
     */
    public static QuartzTask getInstance(QuartzJob quartzJob){
       return new QuartzTask(quartzJob);
    }

}
