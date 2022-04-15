package com.kangkang.manage.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * @ClassName:  TbErpUser
 * @Description:  
 * @Author shaochunhai
 * @Date 2022-02-28 11:34:22 
 */
@Data
@TableName("tb_erp_user")
public class TbErpUser {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 用户名
	 */
    @TableField(value = "username" ,jdbcType = JdbcType.CHAR)
    private String username;

	/**
	 * 用户密码
	 */
	@TableField(value = "password" ,jdbcType = JdbcType.CHAR)
	private String password;

	/**
	 * 电话号码，有可能是通过微信注册
	 */
	@TableField(value = "telephone" ,jdbcType = JdbcType.CHAR)
	private String telephone;

	/**
	 * 身份证号码
	 */
    @TableField(value = "identity_card" ,jdbcType = JdbcType.VARCHAR)
    private String identityCard;

	/**
	 * 身份证正反面照片的地址
	 */
    @TableField(value = "identity_image" ,jdbcType = JdbcType.VARCHAR)
    private String identityImage;

	/**
	 * 真实姓名，和身份证一样
	 */
    @TableField(value = "real_name" ,jdbcType = JdbcType.VARCHAR)
    private String realName;

	/**
	 * 创建时间
	 */
    @TableField(value = "create_date" ,jdbcType = JdbcType.TIMESTAMP
	,fill = FieldFill.INSERT)
    private Date createDate;

	/**
	 * 更新时间
	 */
    @TableField(value = "update_date" ,jdbcType = JdbcType.TIMESTAMP,
	fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

	/**
	 * 现住址
	 */
    @TableField(value = "nowaddress" ,jdbcType = JdbcType.VARCHAR)
    private String nowaddress;


}
