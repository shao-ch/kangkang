package com.kangkang.controller;

import com.kangkang.entity.KangkangUser;
import com.kangkang.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @ClassName: ManageController
 * @Author: shaochunhai
 * @Date: 2021/8/5 2:55 下午
 * @Description: TODO
 */
@RestController
@RequestMapping("kangkang")
public class ManageController {
    private static final Logger logger = LoggerFactory.getLogger(ManageController.class);

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param kangkangUser
     * @return
     */
    @PostMapping("/aaa")
    public ResponseEntity<String> save(@RequestBody KangkangUser kangkangUser){

        ResponseEntity save;
        try {
            save = userService.save(kangkangUser);
        } catch (Exception e) {
            logger.error("调用失败："+e.getMessage());
            e.printStackTrace();
            save=ResponseEntity.status(500).body(e.getMessage());
        }
        return save;
    }

    /**
     * 用户注册
     * @param id
     * @return
     */
    @GetMapping("/bbb")
    public String bbb(int id){
        String bbb="";
        try {
             bbb = userService.bbb(id);
        } catch (Exception e) {
            logger.error("调用失败："+e.getMessage());
            e.printStackTrace();
        }

        return bbb;
    }

}
