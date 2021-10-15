package com.kangkang.controller;


import com.alibaba.fastjson.JSONObject;
import com.kangkang.manage.entity.TbAddress;
import com.kangkang.manage.entity.TbUser;
import com.kangkang.manage.viewObject.TbAdressVO;
import com.kangkang.service.UserService;
import com.kangkang.tools.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ManageController
 * @Author: shaochunhai
 * @Date: 2021/8/5 2:55 下午
 * @Description: TODO
 */
@RestController
@RequestMapping("api")
public class ManageController {
    private static final Logger logger = LoggerFactory.getLogger(ManageController.class);

    @Resource
    private UserService userService;

    /**
     * 用户登陆
     * @param tbUser
     * @return
     */
    @PostMapping("/signIn")
    public ResponseCode<String> kkLogin(@RequestBody TbUser tbUser){

        ResponseCode save;
        //首先查询用户存不存在，不存在就去微信调取用户信息然后保存
        try {
           TbUser user= userService.selectUser(tbUser);

           //如果用户为null，要为该用户在我们系统生成一个用户
           if (user==null){
               userService.save(tbUser);
           }
           save=ResponseCode.message(200,"登陆成功","success");
        } catch (Exception e) {
            logger.error("调用失败："+e.getMessage());
            save=ResponseCode.message(500,"登陆失败","服务异常");
        }
        return save;
    }

    /**
     * 收货地址查询
     * @param json
     * @return
     */
    @ResponseBody
    @PostMapping("/queryAddress")
    public ResponseCode<List<TbAddress>> queryAddress(@RequestBody String json){
        logger.info("=====收货地址查询======,接收参数为：【"+ json+"】");
        ResponseCode save;
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            String openId = (String) jsonObject.get("openId");
            List<TbAddress> address= userService.selectAddress(openId);
            save=ResponseCode.message(200,address,"success");
        } catch (Exception e) {
            logger.error("调用失败："+e.getMessage());
            save=ResponseCode.message(500,null,"服务异常");
        }
        return save;
    }


    /**
     * 保存地址
     * @param vo
     * @return
     */
    @PostMapping("/commitAddress")
    public ResponseCode<Void> commitAddress(@RequestBody TbAdressVO vo){

        ResponseCode save;
        logger.info("=====保存地址======,接收参数为：【"+ JSONObject.toJSONString(vo)+"】");
        //首先查询用户存不存在，不存在就去微信调取用户信息然后保存
        try {
          Map<String,Object> result=userService.commitAddress(vo);
          if (result.get("result").equals("success")){
              save=ResponseCode.message(200,null,"success");
          }else {
              save=ResponseCode.message(200,null,result.get("result").toString());
          }

        } catch (Exception e) {
            logger.error("调用失败："+e.getMessage());
            save=ResponseCode.message(500,null,"服务异常");
        }
        return save;
    }


    /**
     * 删除地址
     * @param vo
     * @return
     */
    @PostMapping("/deleteAddress")
    public ResponseCode<Void> deleteAddress(@RequestBody TbAdressVO vo){

        ResponseCode save;
        logger.info("=====删除地址======,接收参数为：【"+ JSONObject.toJSONString(vo)+"】");


        try {
            userService.deleteAddress(vo);

            if (vo.getId()==null){
                return ResponseCode.message(500,null,"传参有误");
            }
            save=ResponseCode.message(200,null,"success");
        } catch (Exception e) {
            logger.error("=========删除地址========调用失败："+e.getMessage());
            save=ResponseCode.message(500,null,"服务异常");
        }
        return save;
    }
}
