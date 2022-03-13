package com.kangkang.ERP.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.kangkang.ERP.ERPUserFeign;
import com.kangkang.ERP.service.ERPUserService;
import com.kangkang.manage.entity.TbErpUser;
import com.kangkang.utils.AliSmsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: ERPUerController
 * @Author: shaochunhai
 * @Date: 2022/2/28 11:40 上午
 * @Description: TODO
 */
@Slf4j
@RestController
public class ERPUerController implements ERPUserFeign {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ERPUserService erpUserService;

    /**
     * 注册时查询此用户是否已经被注册了
     * @param tbErpUser
     * @return
     */
    @Override
    public TbErpUser selectErpUser(TbErpUser tbErpUser) {
        return erpUserService.selectErpUser(tbErpUser);
    }

    /**
     * 用户注册信息
     * @param tbErpUser
     * @return
     */
    @Override
    public TbErpUser saveErpUser(TbErpUser tbErpUser) {
        return erpUserService.save(tbErpUser);
    }


    /**
     * 短信发送验证码
     * @param telephone
     * @return
     */
    @Override
    public String sendVerifyCode(String telephone) {
        //获取验证码
        String rodomNum = AliSmsUtils.getRodomNum();
        //将验证码存入redis，并设置15分钟过期，key位电话号码
        redisTemplate.opsForValue().set(telephone,rodomNum,15*60, TimeUnit.SECONDS);
        //发送短信
        try {
            SendSmsResponse sendSmsResponse = AliSmsUtils.sendSms(rodomNum, telephone);
            log.info("====阿里云返回的信息为===="+sendSmsResponse.getMessage());
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                return "ok";
            } else {
                return sendSmsResponse.getMessage();
            }

        } catch (ClientException e) {
            log.error("==发送短信失败==",e);
            return e.getMessage();
        }
    }
}
