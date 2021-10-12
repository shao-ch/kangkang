package com.kangkang.manage.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * @ClassName:  TbQuartz
 * @Description:  
 * @Author shaochunhai
 * @Date 2021-10-12 16:05:19 
 */
@Data
@TableName("tb_quartz")
public class TbQuartz {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 类的小驼峰
	 */
    @TableField(value = "class_key" ,jdbcType = JdbcType.VARCHAR)
    private String classKey;

	/**
	 * 类的全县命名
	 */
    @TableField(value = "class_value" ,jdbcType = JdbcType.VARCHAR)
    private String classValue;

	/**
	 * 描述
	 */
    @TableField(value = "desc" ,jdbcType = JdbcType.VARCHAR)
    private String desc;


	/**
	 * 是否开启：0-代表未开启，1-代表开启
	 */
	@TableField(value = "isOpen" ,jdbcType = JdbcType.CHAR)
	private String isOpen;

	/**
	 * 定时任务表达式
	 */
    @TableField(value = "cron_press" ,jdbcType = JdbcType.VARCHAR)
    private String cronPress;

	/**
	 * 更新时间
	 */
    @TableField(value = "update_time" ,jdbcType = JdbcType.TIMESTAMP,update = "now()")
    private Date updateTime;

	/**
	 * 创建时间
	 */
    @TableField(value = "create_time" ,jdbcType = JdbcType.TIMESTAMP,update = "now()")
    private Date createTime;

}
