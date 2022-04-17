package com.kangkang.ERP;

import com.kangkang.manage.entity.TbErpUser;
import com.kangkang.manage.viewObject.TbErpUserVO;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @InterfaceName: ERPUserFeign  erp的用户信息
 * @Author: shaochunhai
 * @Date: 2022/2/28 11:30 上午
 * @Description: TODO
 */
public interface ERPUserFeign {


    /**
     * 后台用户注册时查询此用户是否存在
     * @param tbUser
     * @return
     */
    @LoadBalanced
    @PostMapping(value = "manage/selectErpUser/")
    TbErpUser selectErpUser(@RequestBody TbErpUser tbErpUser);

    /**
     * 用户注册信息
     * @param tbUser
     * @return
     */
    @LoadBalanced
    @PostMapping(value = "manage/saveErpUser/")
    TbErpUser saveErpUser(@RequestBody TbErpUser tbErpUser);

    @LoadBalanced
    @PostMapping(value = "manage/sendVerifyCode/")
    Map<String,Object> sendVerifyCode(@RequestParam("telephone") String telephone);


    /**
     * 后台管理系统登录
     * @param tbErpUser
     * @return
     */
    @LoadBalanced
    @PostMapping(value = "manage/ERPLogin/")
    Map<String,Object> erpLogin(@RequestBody TbErpUserVO tbErpUser);
}
