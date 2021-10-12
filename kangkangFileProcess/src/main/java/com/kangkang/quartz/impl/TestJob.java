package com.kangkang.quartz.impl;

import com.kangkang.quartz.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TestJob  定时任务测试类
 * @Author: shaochunhai
 * @Date: 2021/10/12 5:06 下午
 * @Description: TODO
 */
//@Component
@Slf4j
public class TestJob implements QuartzJob {

    @Override
    public void excute() {


        log.info("-------定时任务正在执行--------");

    }
}
