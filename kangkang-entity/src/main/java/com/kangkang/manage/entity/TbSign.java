package com.kangkang.manage.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * @ClassName:  TbSign
 * @Description:  
 * @Author shaochunhai
 * @Date 2021-10-12 14:36:05 
 */
@Data
@TableName("tb_sign")
public class TbSign {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 用户唯一标识
	 */
    @TableField(value = "openid" ,jdbcType = JdbcType.VARCHAR)
    private String openid;

	/**
	 * 签到日期
	 */
    @TableField(value = "sign_date" ,jdbcType = JdbcType.DATE)
    private Date signDate;

	/**
	 * 开始签到时间
	 */
    @TableField(value = "start_sign_time" ,jdbcType = JdbcType.TIMESTAMP)
    private Date startSignTime;

	/**
	 * 结束签到时间
	 */
    @TableField(value = "end_sign_time" ,jdbcType = JdbcType.TIMESTAMP)
    private Date endSignTime;

	/**
	 * 标识：0-打卡成功，1-打卡失败
	 */
    @TableField(value = "flag" ,jdbcType = JdbcType.CHAR)
    private String flag;

	/**
	 * 打卡地址
	 */
    @TableField(value = "sign_address" ,jdbcType = JdbcType.VARCHAR)
    private String signAddress;

	/**
	 * 清除属性
	 */
	public void clear() {
		id=null;
		openid=null;
		signAddress=null;
		signDate=null;
		startSignTime=null;
		endSignTime=null;
		//默认打开失败
		flag="1";
	}
}
