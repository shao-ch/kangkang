package com.kangkang.file.service;

import com.kangkang.manage.entity.TbQuartz;
import com.kangkang.manage.entity.TbSign;

import java.util.List;

/**
 * @InterfaceName: QuartzService  定时器的服务
 * @Author: shaochunhai
 * @Date: 2021/10/12 2:22 下午
 * @Description: TODO
 */
public interface QuartzService {

    /**
     * 查询所有用户的openid
     * @return
     */
    List<String> queryAllUser();

    /**
     * 保存签到信息
     * @param tbSign
     */
    void insertTbSign(List<TbSign> tbSign);


    /**
     * 查询所有的定时任务信息
     * @return
     */
    List<TbQuartz> selectQuartzInfo();

}
