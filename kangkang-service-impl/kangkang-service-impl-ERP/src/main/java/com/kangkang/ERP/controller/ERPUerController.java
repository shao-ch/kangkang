package com.kangkang.ERP.controller;

import com.alibaba.nacos.common.util.Md5Utils;
import com.kangkang.ERP.ERPUserFeign;
import com.kangkang.ERP.config.TXSmsUtils;
import com.kangkang.ERP.service.ERPUserService;
import com.kangkang.manage.entity.TbErpUser;
import com.kangkang.manage.dtoObject.TbErpUserDTO;
import com.kangkang.tools.AESUtils;
import com.kangkang.tools.ConverUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    public Map<String,Object> sendVerifyCode(String telephone) {
        HashMap<String, Object> map = new HashMap<>();
        //获取验证码
        String rodomNum = TXSmsUtils.getRodomNum();
        //发送短信
        try {
            //将验证码存入redis，并设置15分钟过期，key位电话号码
            redisTemplate.opsForValue().set(telephone,rodomNum,5*60, TimeUnit.SECONDS);
            String[] phones={telephone};
            String[] context={rodomNum,"5"};
//            SendStatus sendStatus = txSmsUtils.sendSMS(phones, context, "1");

//            log.info("====腾讯云短信返回的信息为===="+sendStatus.getMessage());
//            if (sendStatus.getCode() != null && sendStatus.getCode().equals("Ok")) {
//                map.put("flag","ok");
//                map.put("message",rodomNum);
//                return map;
//            } else {
//                map.put("flag","fail");
//                map.put("message",sendStatus.getMessage());
//                return map;
//            }
            map.put("flag","ok");
            map.put("message",rodomNum);
            return map;

        } catch (Exception e) {
            log.error("==发送短信失败==",e);
            map.put("flag","error");
            map.put("message",e.getMessage());
            return map;
        }
    }


    /**
     * 后台管理系统登录
     * @param tbErpUser
     * @return
     */
    @Override
    public Map<String,Object> erpLogin(TbErpUserDTO tbErpUser) {

        HashMap<String, Object> map = new HashMap<>();

        /**
         * 入参数据
         */
        TbErpUser userRc = new TbErpUser();
        BeanUtils.copyProperties(tbErpUser,userRc);
        /**
         * 用户名和密码
         */
        if ("0".equals(tbErpUser.getLoginType())) {

            /**
             * 查询是否有此用户
             */
            //这里将密码进行加密处理
            String password=userRc.getPassword();
            //这里做aes的解密处理然后再做MD5加密做判断
            password = AESUtils.aesDecrypt(password);
            log.info("解密后的密码为："+password);
            password=Md5Utils.getMD5(password, "UTF-8");
            userRc.setPassword(password);

            TbErpUser user = erpUserService.selectErpByUserAndPassword(userRc);;

            if (user==null){
                log.info("用户名或密码错误");
                map.put("flag","fail");
                map.put("message","用户名或密码错误！");
                return map;
            }else {
                map.put("flag","ok");
                map.put("user",user);
                map.put("message","登录成功");
                return map;
            }
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
            TbErpUser user = erpUserService.selectErpUser(userRc);;

            if (user==null){
                //用户注册
                String userName = ConverUtils.getRandomUserName();
                userRc.setUsername(userName);
                //设置用户密码，默认密码为123456
                userRc.setPassword(Md5Utils.getMD5("123456", "UTF-8"));
                erpUserService.save(userRc);
                //这里要讲插入的数据交给user
                user=userRc;
                log.info("此用户为登录的新用户，此用户注册成功！！！");
            }
            map.put("flag","ok");
            map.put("user",user);
            map.put("message","登录成功");
        }

        return map;
    }
}
