package com.kangkang.ERP.login.controller;

import com.alibaba.fastjson.JSONObject;
import com.kangkang.ERP.login.service.ERPUserService;
import com.kangkang.manage.entity.TbErpUser;
import com.kangkang.manage.entity.TbUser;
import com.kangkang.tools.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    /**
     * 后台用户注册
     * @param tbErpUser
     * @return
     */
    @ResponseBody
    @PostMapping("/userRegister")
    public ResponseCode<String> kkLogin(@RequestBody TbErpUser tbErpUser){

        ResponseCode save;
        //首先查询用户存不存在，通过电话号码，或者微信的openid，目前只有两个
        try {
            log .info("===后台用户注册====,传入的参数为："+ JSONObject.toJSONString(tbErpUser));

            if (StringUtils.isEmpty(tbErpUser.getTelephone())
                    &&StringUtils.isEmpty(tbErpUser.getOpenid())){
                return ResponseCode.message(500,"电话和微信标识不能同时为空","success");
            }

            TbErpUser user= erpUserService.selectErpUser(tbErpUser);

            //如果用户为null，要为该用户在我们系统生成一个用户
            if (user==null){
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
    @ResponseBody
    @PostMapping("/sendVerifyCode")
    public ResponseCode<String> sendVerifyCode(@RequestParam(value = "telephone",required = true) String telephone){
        ResponseCode save;
        log.info("===验证码生成并发送短信====,传入的参数为："+ JSONObject.toJSONString(telephone));
        try {
            String flag=erpUserService.sendVerifyCode(telephone);
            if ("ok".equals(flag))
                save = ResponseCode.message(200, flag, "success");
            save = ResponseCode.message(200, flag, "success");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("=====登录验证码异常======",e);
            save = ResponseCode.message(200, "服务异常", "success");
        }

        return save;
    }



}
