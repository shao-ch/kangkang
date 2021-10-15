package com.kangkang.controller;

import com.kangkang.manage.entity.TbAddress;
import com.kangkang.manage.entity.TbUser;
import com.kangkang.manage.viewObject.TbAdressVO;
import com.kangkang.service.KangkangFeign;
import com.kangkang.service.ManageService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
     * @param tbUser
     * @return
     */
    @Override
    public ResponseEntity<String> save(TbUser tbUser){

        if (tbUser ==null){
            return ResponseEntity.status(200).body("用户不能为null");
        }


        if(StringUtils.isEmpty(tbUser.getOpenid())){
            return ResponseEntity.status(200).body("用户名不能为null");
        }

        try {
            manageService.save(tbUser);
        } catch (Exception e) {
            logger.error("注册账号服务异常："+e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("服务异常");
        }


        return ResponseEntity.status(200).body("注册成功！");
    }


    /**
     * 查询用户信息
     * @param tbUser
     * @return
     */
    @Override
    public TbUser selectUser(TbUser tbUser) {
        return manageService.selectUser(tbUser);
    }


    /**
     * 通过openId查询用户地址
     * @param openId
     * @return
     */
    @Override
    public List<TbAddress> selectAddress(String openId) {
        return manageService.selectAddress(openId);
    }


    /**
     * 新增收货地址
     * @param vo
     * @return
     */
    @Override
    public Map<String,Object> commitAddress(TbAdressVO vo) {
        return manageService.commitAddress(vo);
    }

    /**
     * 删除地址
     * @param vo
     * @return
     */
    @Override
    public void deleteAddress(TbAdressVO vo) {
        manageService.deleteAddress(vo);
    }
}