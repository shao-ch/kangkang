package com.kangkang.ERP.login.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.util.Md5Utils;
import com.kangkang.ERP.login.service.ERPUserService;
import com.kangkang.manage.entity.TbErpUser;
import com.kangkang.manage.viewObject.TbErpUserVO;
import com.kangkang.tools.ConverUtils;
import com.kangkang.tools.JwtUtils;
import com.kangkang.tools.KBase64Utils;
import com.kangkang.tools.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import sun.security.rsa.RSAUtil;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: UserLoginController  后台管理系统用户登录
 * @Author: shaochunhai
 * @Date: 2022/2/28 10:18 上午
 * @Description: TODO
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class UserLoginController {

    @Autowired
    private ERPUserService erpUserService;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 后台用户注册
     * @param tbErpUser
     * @return
     */
    @ResponseBody
    @PostMapping("/userRegister")
    public ResponseCode<String> kkLogin(@RequestBody TbErpUserVO tbErpUser){

        ResponseCode save;
        //首先查询用户存不存在，通过电话号码，或者微信的openid，目前只有两个
        try {
            log .info("===后台用户注册====,传入的参数为："+ JSONObject.toJSONString(tbErpUser));

            if (StringUtils.isEmpty(tbErpUser.getTelephone())
                    ||StringUtils.isEmpty(tbErpUser.getUsername())||
                    StringUtils.isEmpty(tbErpUser.getPassword())){
                return ResponseCode.message(500,"用户名/密码/电话不能为空","success");
            }

            /**
             * 通过电话号码或者用户名去查询
             */
            TbErpUser user= erpUserService.selectErpUser(tbErpUser);

            //如果用户为null，要为该用户在我们系统生成一个用户
            if (user==null){
                TbErpUser userInfo = new TbErpUser();
                //属性转移
                BeanUtils.copyProperties(tbErpUser,userInfo);

                //通过不同的方式获取不同的用户名 0-代表电话注册，1-用户注册
                if ("0".equals(tbErpUser.getRegistyType())){
                    String userName = ConverUtils.getRandomUserName();
                    userInfo.setUsername(userName);
                    //设置用户密码，默认密码为123456
                    userInfo.setPassword(Md5Utils.getMD5("123456", "UTF-8"));
                }

                user= erpUserService.saveErpUser(tbErpUser);
                save=ResponseCode.message(200,user,"success");
            }else {
                save=ResponseCode.message(200,"此用户已被注册","success");
            }

        } catch (Exception e) {
            log.error("调用失败："+e.getMessage());
            save=ResponseCode.message(500,"登陆失败","服务异常");
        }
        return save;
    }

    /**
     * 登录验证码生成并发送短信
     * @param telephone
     * @return
     */
    @PostMapping("/sendVerifyCode")
    public ResponseCode<String> sendVerifyCode(@RequestParam(value = "telephone",required = true) String telephone){
        ResponseCode save;
        log.info("===验证码生成并发送短信====,传入的参数为："+ JSONObject.toJSONString(telephone));
        try {
            String flag=erpUserService.sendVerifyCode(telephone);
            if ("ok".equals(flag)) {
                save = ResponseCode.message(200, flag, "success");
            } else {
                save = ResponseCode.message(200, flag, "failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("=====登录验证码异常======",e);
            save = ResponseCode.message(200, "服务异常", "success");
        }

        return save;
    }

    /**
     * 后台管理系统登录
     * @param tbErpUser
     * @return
     */
    @ResponseBody
    @PostMapping("/ERPLogin")
    public ResponseCode<Map<String,Object>> erpLogin(@RequestBody TbErpUserVO tbErpUser){
        ResponseCode save;
        log.info("===康康后台管理系统登录====,传入的参数为："+ JSONObject.toJSONString(tbErpUser));
        try {
            Map<String,Object> map=erpUserService.erpLogin(tbErpUser);
            /**
             * 判断信息
             */
            if ("ok".equals(map.get("flag"))){

                //将token存入redis
                String jwtInfo = JwtUtils.generateJsonERPToken(map);

                TbErpUser user = (TbErpUser) map.get("user");

                //token的生成规则为：用户名+":"+电话号码
                String token = KBase64Utils.encode(user.getUsername() + ":" + user.getTelephone());
                //设置token过期时间为40分钟
                redisTemplate.opsForValue().set(token,jwtInfo,40*60, TimeUnit.SECONDS);
                //将 token返回给前端
                map.put("token",token);
                save = ResponseCode.message(200, map, "登陆成功");
            }else {
                save = ResponseCode.message(200, null, "登录失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("=====登录异常======",e);
            save = ResponseCode.message(200, "服务异常", "success");
        }

        return save;
    }

}
