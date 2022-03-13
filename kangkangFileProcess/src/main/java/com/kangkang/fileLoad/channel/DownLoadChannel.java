package com.kangkang.fileLoad.channel;

import com.alibaba.fastjson.JSONObject;
import com.kangkang.fileLoad.entity.Message;
import com.kangkang.fileLoad.service.TbAsynLoadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: DownLoadChannel   文件处理层，也就是生成excel的地方
 * @Author: shaochunhai
 * @Date: 2022/3/12 9:36 上午
 * @Description: TODO
 */
@Slf4j
@Component
public class DownLoadChannel extends AbstractDataChannel {

    @Autowired
    private TbAsynLoadService tbAsynLoadService;

    //这里具体是做读的逻辑
    @Override
    public void read() {
        //首先去将即将执行的任务设置为处理中
        ScheduledExecutorService schedulForStatus = Executors.newScheduledThreadPool(1);

        schedulForStatus.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                log.info("=================正在将创建的任务更新为处理中=================");
                tbAsynLoadService.updateCreateStatus();
            }
        },0,10,TimeUnit.SECONDS);

        //创建一个定时任务去执行poll，也就是放入队列操作
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);

        //这个方法表示：如果小于1秒，就在1秒结束后执行，如果大于一秒，就在停止任务的时候马上去执行
        scheduled.scheduleWithFixedDelay(new Runnable() {

            List<Message> list;
            @Override
            public void run() {
                try {
                    log.info("=================获取任务=================");
                    list = tbAsynLoadService.querySqlTask();

                    log.info("=================任务为=================："+JSONObject.toJSONString(list));
                    if (list.isEmpty()) {
                        log.info("=========没有需要执行的excel任务=============");
                        return;
                    }
                    //将数据放入阻塞队列
                    for (Message message : list) {
                        queue.put(message);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.error("将excel文件放入队列异常：",e);
                }

            }
        },0,10, TimeUnit.SECONDS);
    }


    //这里具体是做写的逻辑
    @Override
    public void write() {
        log.info("============================我要写数据了============================");

        while (true){
            try {
                //这里不需要判空，如果没有取到数据就会阻塞
                final Message take = queue.take();
                log.info("=================获取任务=================："+JSONObject.toJSONString(take));
                //这里开启多线程去执行任务
                getExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        //将参数转map
                        HashMap<String,Object> map;
                        String params = take.getParams();
                        log.info("=================获取sql=================："+take.getExcuteSql());
                        log.info("=================获取参数=================："+params);
                        if (params==null){
                            map=new HashMap<>();
                        }else {
                            map=JSONObject.parseObject(params, (Type) Map.class);
                        }
                        Map<String,Object> result=tbAsynLoadService.executorSql(take.getExcuteSql(),map);
                        log.info("=================输出结果=================："+JSONObject.toJSONString(result));
                        //这里就是文件处理的逻辑

                        //然后将状态更新为已完成


                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("获取信息异常：",e);
            }
        }
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
