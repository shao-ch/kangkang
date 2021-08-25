package com.kangkang.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: KangkangUser  康康商城用户表
 * @Author: shaochunhai
 * @Date: 2021/8/8 2:51 下午
 * @Description: TODO
 */
@Data
@TableName("tb_user")
public class TbUser {
    //主键id
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //微信的用户id
    private String openid;

    //真实姓名
    private String  name;

    //电话号码
    private String telephone;

    //状态  0-代表有效  1-无效
    private String status;

    //创建时间
    private Date createDate;

    //更新时间
    private Date updateDate;
}
