package com.kangkang.config;

import com.kangkang.fileLoad.start.Starter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @ClassName: ChannelStarter  通道启动器
 * @Author: shaochunhai
 * @Date: 2022/3/11 5:25 下午
 * @Description: TODO
 * 下面的@WebListener这种方式需要在启动类上加上@ServletComponentScan 这个注解和@MapperScan类似
 * 作用其实和@Component产生的效果是一样的
 */
@WebListener
public class ChannelStarter implements ServletContextListener{

    @Autowired
    private Starter starter;

    //安全的启动管道通信
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        starter.start();
        System.out.println("===============我启动了！！！=================");
    }

    //在spring容器退出的时候调用销毁方法，也就是安全的切断管道通信
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("================我消失了！！！==================");
        starter.stop();
    }
}
