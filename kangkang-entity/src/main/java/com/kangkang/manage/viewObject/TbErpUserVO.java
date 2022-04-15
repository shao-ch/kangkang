package com.kangkang.manage.viewObject;
import com.baomidou.mybatisplus.annotation.*;
import com.kangkang.manage.entity.TbErpUser;
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
public class TbErpUserVO extends TbErpUser {

	/**
	 * 验证码
	 */
	private String verifyCode;

	/**
	 * 0-代表用户登录，1-代表短信验证码登录
	 */
	private String loginType;

	/**
	 * 0-代表电话注册，1-用户注册
	 */
	private String registyType;



}
