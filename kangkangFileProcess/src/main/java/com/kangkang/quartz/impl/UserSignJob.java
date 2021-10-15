package com.kangkang.quartz.impl;

import com.kangkang.file.service.QuartzService;
import com.kangkang.manage.entity.TbSign;
import com.kangkang.quartz.QuartzJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: UserSignJob  用户签到的跑批任务
 * @Author: shaochunhai
 * @Date: 2021/10/12 2:13 下午
 * @Description: TODO
 */
public class UserSignJob implements QuartzJob {

    @Autowired
    private QuartzService quartzService;

    @Override
    public void excute() {
        //第一步去查询用户信息，

        List<String> users = quartzService.queryAllUser();

        List<TbSign> list = new ArrayList<>();
        for (String openid : users) {
            TbSign tbSign = new TbSign();
            //存用户
            tbSign.setOpenid(openid);
            //存当前日期
            tbSign.setSignDate(new Date());
            list.add(tbSign);
        }
        //然后创建信息到签到表中
        quartzService.insertTbSign(list);


    }
}
