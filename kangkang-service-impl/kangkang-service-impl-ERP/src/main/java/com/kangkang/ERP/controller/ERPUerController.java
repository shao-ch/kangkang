package com.kangkang.ERP.controller;

import com.kangkang.ERP.ERPUserFeign;
import com.kangkang.ERP.config.TXSmsUtils;
import com.kangkang.ERP.service.ERPUserService;
import com.kangkang.manage.entity.TbErpUser;
import com.kangkang.manage.viewObject.TbErpUserVO;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
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

    @Autowired
    private TXSmsUtils txSmsUtils;
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
        String rodomNum = TXSmsUtils.getRodomNum();
        //发送短信
        try {
            //将验证码存入redis，并设置15分钟过期，key位电话号码
            redisTemplate.opsForValue().set(telephone,rodomNum,5*60, TimeUnit.SECONDS);
            String[] phones={telephone};
            String[] context={rodomNum,"5"};
            SendStatus sendStatus = txSmsUtils.sendSMS(phones, context, "1");
            log.info("====腾讯云短信返回的信息为===="+sendStatus.getMessage());
            if (sendStatus.getCode() != null && sendStatus.getCode().equals("Ok")) {
                return "ok";
            } else {
                return sendStatus.getMessage();
            }

        } catch (Exception e) {
            log.error("==发送短信失败==",e);
            return e.getMessage();
        }
    }


    /**
     * 后台管理系统登录
     * @param tbErpUser
     * @return
     */
    @Override
    public Map<String,Object> erpLogin(TbErpUserVO tbErpUser) {

        HashMap<String, Object> map = new HashMap<>();

        /**
         * 微信登录
         */
        if ("0".equals(tbErpUser.getLoginType())) {

        } else if ("1".equals(tbErpUser.getLoginType())) {  //短信验证码登录
            //查看验证码
            Object o = redisTemplate.opsForValue().get(tbErpUser.getTelephone());

            //校验验证码
            if (!tbErpUser.getVerifyCode().equals(o)){
                map.put("flag","fail");
                map.put("message","验证码不正确");
                return map;
            }
            /**
             * 查询是否有此用户
             */
            TbErpUser user = selectErpUser(tbErpUser);

            if (user==null){
                //用户注册
                saveErpUser(tbErpUser);
                log.info("此用户为登录的新用户，此用户注册成功！！！");
            }
            map.put("flag","ok");
            map.put("user",user);
            map.put("message","登录成功");
        }

        return map;
    }
}
