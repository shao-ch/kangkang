package com.kangkang.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: TbAddress   收货地址表
 * @Author: shaochunhai
 * @Date: 2021/8/21 4:27 下午
 * @Description: TODO
 */
@Data
@TableName("tb_address")
public class TbAddress {

    @TableId(value = "id",type = IdType.AUTO)
    //主键
    private Long id;
    //用户id
    private Long userId;
    //收货人
    private String consignee;
    //收货人电话
    private String consiTelephone;
    //收货人地址的最后一级
    private long areaid;
    //手输入的具体地址
    private String detailAddress;
    //优先级：0-设置默认地址，1-普通
    private String priority;
    //更新时间
    private Date updateTime;
    //创建时间
    private Date createTime;
}
