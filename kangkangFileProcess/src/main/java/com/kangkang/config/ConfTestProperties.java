package com.kangkang.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ConfTestProperties  这个是不同属性的注入方式，作为学习用的
 * @Author: shaochunhai
 * @Date: 2022/3/12 11:06 上午
 * @Description: TODO
 */
@Component
@ConfigurationProperties(prefix = "student")
public class ConfTestProperties {

    private String email ;
    private String name;
    @Value("${student.uname}")
    private String userName;
    @Value("25")
    private int age ;
    private boolean sex ; //true:男  false:女
    private Date birthday ;
    private Map<String,Object> location ;
    private String[] hobbies ;
    private List<String> skills ;

    private Pet pet;

    public class Pet {
        private String nickName ;//松散写法nick-name
        private String strain ;
    }
}
