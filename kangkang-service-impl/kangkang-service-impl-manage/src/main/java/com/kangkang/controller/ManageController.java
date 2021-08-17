package com.kangkang.controller;

import com.kangkang.entity.KangkangUser;
import com.kangkang.service.KangkangFeign;
import com.kangkang.service.ManageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName: ManageController
 * @Author: shaochunhai
 * @Date: 2021/8/8 3:36 下午
 * @Description: TODO
 */
@RestController
public class ManageController implements KangkangFeign {
    private static final Logger logger = LoggerFactory.getLogger(ManageController.class);
    @Resource
    private ManageService manageService;

    /**
     * 注册用户
     * @param kangkangUser
     * @return
     */
    @Override
    public ResponseEntity<String> save(KangkangUser kangkangUser){

        if (kangkangUser==null){
            return ResponseEntity.status(200).body("用户不能为null");
        }


        if(StringUtils.isEmpty(kangkangUser.getLoginName())){
            return ResponseEntity.status(200).body("用户名不能为null");
        }

        if(StringUtils.isEmpty(kangkangUser.getPassword())){
            return ResponseEntity.status(200).body("密码不能为null");
        }else if (kangkangUser.getPassword().length()<6){
            return ResponseEntity.status(200).body("密码长度不能小于6位");
        }

        try {
            manageService.save(kangkangUser);
        } catch (Exception e) {
            logger.error("注册账号服务异常："+e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("服务异常");
        }


        return ResponseEntity.status(200).body("注册成功！");
    }


    @Override
    public String bbb(int id) {
        return id+10+"";
    }
}