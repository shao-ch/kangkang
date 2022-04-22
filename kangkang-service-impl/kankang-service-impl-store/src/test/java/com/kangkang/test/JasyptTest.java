package com.kangkang.test;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @ClassName: JasyptTest
 * @Author: shaochunhai
 * @Date: 2021/9/7 5:35 下午
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JasyptTest {

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void encryptPwd(){

        System.out.println("stringEncryptor = " + stringEncryptor.encrypt("root123"));
    }
}
