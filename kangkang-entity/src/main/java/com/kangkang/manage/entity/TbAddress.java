package com.kangkang.manage.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import org.apache.ibatis.type.JdbcType;
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
	 * 用户openid
	 */
    @TableField(value = "open_id",jdbcType = JdbcType.VARCHAR)
    private String openId;

	/**
	 * 收货人
	 */
    @TableField(value = "consignee",jdbcType = JdbcType.VARCHAR)
    private String consignee;

	/**
	 * 省名
	 */
    @TableField(value = "province_name",jdbcType = JdbcType.VARCHAR)
    private String provinceName;

	/**
	 * 省代码
	 */
    @TableField(value = "province_code",jdbcType = JdbcType.VARCHAR)
    private String provinceCode;

	/**
	 * 市名
	 */
    @TableField(value = "city_name",jdbcType = JdbcType.VARCHAR)
    private String cityName;

	/**
	 * 市代码
	 */
    @TableField(value = "city_code",jdbcType = JdbcType.VARCHAR)
    private String cityCode;

	/**
	 * 县名称
	 */
    @TableField(value = "county_name",jdbcType = JdbcType.VARCHAR)
    private String countyName;

	/**
	 * 详细地址
	 */
    @TableField(value = "detailAddress",jdbcType = JdbcType.DOUBLE)
    private String detailAddress;

	/**
	 * 县代码
	 */
    @TableField(value = "county_code",jdbcType = JdbcType.VARCHAR)
    private String countyCode;

	/**
	 * 是否是默认：0-默认，1代表普通
	 */
    @TableField(value = "is_default",jdbcType = JdbcType.CHAR)
    private String isDefault;

	/**
	 * 创建时间
	 */
    @TableField(value = "create_time",jdbcType = JdbcType.TIMESTAMP)
    private Date createTime;

	/**
	 * 电话号码
	 */
    @TableField(value = "telephone",jdbcType = JdbcType.VARCHAR)
    private String telephone;

	/**
	 * 更新时间
	 */
    @TableField(value = "update_time",jdbcType = JdbcType.TIMESTAMP)
    private Date updateTime;

}
