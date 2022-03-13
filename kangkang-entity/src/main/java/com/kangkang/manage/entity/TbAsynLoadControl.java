package com.kangkang.manage.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * @ClassName:  TbAsynLoadControl
 * @Description:  
 * @Author shaochunhai
 * @Date 2022-03-12 12:56:25 
 */
@Data
@TableName("tb_asyn_load_control")
public class TbAsynLoadControl {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 需要执行的sql
	 */
    @TableField(value = "excute_sql" ,jdbcType = JdbcType.VARCHAR)
    private String excuteSql;

	/**
	 * sql需要的参数，json格式
	 */
    @TableField(value = "params" ,jdbcType = JdbcType.VARCHAR)
    private String params;

	/**
	 * 状态：0-新建，1-处理中，2-处理成功，3-处理失败
	 */
    @TableField(value = "status" ,jdbcType = JdbcType.CHAR)
    private String status;

	/**
	 * 处理之后的结果，成功，或者失败，或者失败原因之类的
	 */
    @TableField(value = "result" ,jdbcType = JdbcType.VARCHAR)
    private String result;

	/**
	 * 生成excel的路径
	 */
    @TableField(value = "path" ,jdbcType = JdbcType.VARCHAR)
    private String path;

	/**
	 * 类名
	 */
	@TableField(value = "class_name" ,jdbcType = JdbcType.VARCHAR)
	private String className;

	/**
	 * 创建时间
	 */
    @TableField(value = "create_time" ,jdbcType = JdbcType.TIMESTAMP)
    private Date createTime;

	/**
	 * 更新时间
	 */
    @TableField(value = "update_time" ,jdbcType = JdbcType.TIMESTAMP)
    private Date updateTime;

}
