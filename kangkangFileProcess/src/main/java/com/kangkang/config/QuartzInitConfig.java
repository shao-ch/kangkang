package com.kangkang.config;

import com.kangkang.file.service.QuartzService;
import com.kangkang.file.utils.QuartzTask;
import com.kangkang.manage.entity.TbQuartz;
import com.kangkang.quartz.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;

/**
 * @ClassName: QuartzInitConfig  这里是初始化定时任务的类
 * @Author: shaochunhai
 * @Date: 2021/10/12 2:04 下午
 * @Description: TODO
 */
@Slf4j
@Configuration
@EnableScheduling
public class QuartzInitConfig implements SchedulingConfigurer {


    @Autowired
    private QuartzService quartzService;

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
            //获取全县命名
            String classValue = tbQuartz.getClassValue();
            //反射创建实例
            try {
                /**
                 * 判断是否是继承了定时任务接口的定时任务，不是不允许执行
                 */
                Class<?> aClass = Class.forName(classValue);
                if (!(aClass.newInstance() instanceof QuartzJob)){
                    log.error("【"+tbQuartz.getClassValue()+"】=====此任务处于不属于定时任务====：");
                    continue;
                }
                //判断定时任务是否开启
                if (tbQuartz.getIsOpen().equals("0")){
                    log.error("【"+tbQuartz.getClassValue()+"】=====此定时任务处于关闭状态====：");
                    //未开启
                    continue;
                }
                //获取具体定时任务信息
                QuartzJob job= (QuartzJob) aClass.newInstance();
                //获取定时任务的定时器
                TriggerTask triggerTask = new TriggerTask(QuartzTask.getInstance(job), new CronTrigger(tbQuartz.getCronPress()));
                //交给spring的schedule
                taskRegistrar.addTriggerTask(triggerTask);
            } catch (Exception e) {
                log.error("【"+tbQuartz.getClassValue()+"】=====这个定时任务发生异常。异常信息为====："+e);
                continue;
            }

        }

    }
}
