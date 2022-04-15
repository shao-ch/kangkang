package com.kangkang.config;

import com.kangkang.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: InitDataConfig  初始化配置信息
 * @Author: shaochunhai
 * @Date: 2021/9/22 10:21 上午
 * @Description: TODO
 */
@Slf4j
@Configuration
public class InitDataConfig implements InitializingBean, ServletContextAware {

    @Autowired
    private ConfigService configService;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("这里可以做一些属性的初始化的工作。。。。。");
    }

    @Override
    public void setServletContext(ServletContext servletContext) {

        log.info("=========开始获取配置信息============");
        //容器启动的时候去获取配置信息
        try {
            List<Map<String,String>> list=configService.initConfigData();
            HashMap<String, String> result = new HashMap<>();
            for (Map<String, String> map : list) {
                result.put(map.get("confKey"),map.get("confValue"));
            }
            ConfigDataContext.configData=result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取配置信息失败："+e);
        }

        log.info(ConfigDataContext.configData.toString());
    }
}
