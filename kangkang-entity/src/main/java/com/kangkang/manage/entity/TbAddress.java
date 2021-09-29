package com.kangkang.manage.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_address")
public class TbAddress {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 用户id
	 */
    private String openId;

	/**
	 * 收货人
	 */
    private String consignee;

	/**
	 * 省名
	 */
    private String provinceName;

	/**
	 * 省代码
	 */
    private String provinceCode;

	/**
	 * 市名
	 */
    private String cityName;

	/**
	 * 市代码
	 */
    private String cityCode;

	/**
	 * 县名称
	 */
    private String countyName;

	/**
	 * 详细地址
	 */
    private String detailAddress;

	/**
	 * 县代码
	 */
    private String countyCode;

	/**
	 * 是否是默认：0-默认，1代表普通
	 */
    private String isDefault;


	/**
	 * 电话号码
	 */
    private String telephone;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
    private Date updateTime;

}
