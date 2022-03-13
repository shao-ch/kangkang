package com.kangkang.fileLoad.start;

import com.kangkang.config.ConfChannelSwitchProperties;
import com.kangkang.fileLoad.channel.Channel;
import com.kangkang.fileLoad.channel.DownLoadChannel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: start  管道注册启动类
 * @Author: shaochunhai
 * @Date: 2022/3/12 10:01 上午
 * @Description: TODO
 */
public class Starter implements ApplicationContextAware {

    private List<Channel> channels;

    @Autowired
    private ConfChannelSwitchProperties switchProperties;

    //启动数据通信
    public void start(){
        //管道启动类
        for (Channel channel : channels) {
            channel.start();
        }
    }

    //停止数据的通信
    public void stop(){
        for (Channel channel : channels) {
            channel.stop();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {

        Map<String, String> switchs = switchProperties.getSwitchs();
        //循环遍历channel开关,然后将对象注入channel容器中
        switchs.forEach((key,value)->{

            System.out.println("==================["+key+"]------------>["+value+"]==================");
            if (value.equals("on")){
               Channel channel= (Channel) context.getBean(key);
               channels.add(channel);
            }
        });
    }
}
