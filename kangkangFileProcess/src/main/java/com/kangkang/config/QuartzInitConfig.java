package com.kangkang.config;

import com.kangkang.file.service.QuartzService;
import com.kangkang.file.utils.QuartzTask;
import com.kangkang.manage.entity.TbQuartz;
import com.kangkang.quartz.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @ClassName: QuartzInitConfig  这里是初始化定时任务的类
 * 定时任务设定的每20秒启动一次，若上一个任务运行的时间超过了执行频率20秒，这个20秒的计时时间标准是以上次任务执行完成之后开始计时。
 * 也就是说这里不会阻塞，同一个任务会等待相同的上个任务执行完才会执行下一个
 * @Author: shaochunhai
 * @Date: 2021/10/12 2:04 下午
 * @Description: TODO
 */
@Slf4j
@Configuration
@EnableScheduling
public class QuartzInitConfig implements SchedulingConfigurer  {


    @Autowired
    private QuartzService quartzService;

    @Autowired
    private ApplicationContext context;

    /**
     * 这里就是执行定时任务的
     *
     * @param taskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        //查询有多少定时任务需要执行
        List<TbQuartz> list=quartzService.selectQuartzInfo();

        if (list.isEmpty()){
            log.info("=============无定时任务需要执行=============");
            return;
        }

        /**
         * 然后将定时任务交给定时任务框架处理
         */
        for (TbQuartz tbQuartz : list) {
            //去除有误的数据
            if (tbQuartz.getClassKey()==null|| StringUtils.isEmpty(tbQuartz.getClassValue())
            ||StringUtils.isEmpty(tbQuartz.getCronPress())){
                continue;
            }
            //获取在spring容器的实例对象
            String classValue = tbQuartz.getClassValue();
            //反射创建实例
            try {
                /**
                 * 判断是否是继承了定时任务接口的定时任务，不是不允许执行
                 */
                Object bean = context.getBean(classValue);

                if (bean instanceof QuartzJob){
                    //获取定时任务的定时器
                    TriggerTask triggerTask = new TriggerTask(QuartzTask.getInstance((QuartzJob)bean), new CronTrigger(tbQuartz.getCronPress()));
                    //交给spring的schedule
                    taskRegistrar.addTriggerTask(triggerTask);
                    //这里设置多线程执行，避免造成任务堆积
                    taskRegistrar.setScheduler(new ScheduledThreadPoolExecutor(5));

                    //taskRegistrar.addFixedRateTask(); 这个方法是说该任务执行完毕后要等待指定的时间在执行下一次，说的同一个任务。

                }else {
                    log.info("=============此类不属于定时任务的类=============");
                    continue;
                }


            } catch (Exception e) {
                log.error("【"+tbQuartz.getClassValue()+"】=====这个定时任务发生异常。异常信息为====："+e);
                continue;
            }

        }

    }

}
