package com.kangkang.store.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * @ClassName:  Tb_Specification
 * @Description:  
 * @Author shaochunhai
 * @Date 2022-04-18 22:53:41 
 */
@Data
@TableName("tb_specification")
public class TbSpecification {


	/**
	 * 主键ID
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 规格名称
	 */
    @TableField(value = "rule_name" ,jdbcType = JdbcType.VARCHAR)
    private String ruleName;

	/**
	 * 规格属性名称
	 */
	@TableField(value = "attr_name" ,jdbcType = JdbcType.VARCHAR)
	private String attrName;


	/**
	 * 规格属性值
	 */
	@TableField(value = "attr_value" ,jdbcType = JdbcType.VARCHAR)
	private String attrValue;


	/**
	 * 规格属性映射，map的json格式
	 */
    @TableField(value = "spec_property" ,jdbcType = JdbcType.VARCHAR)
    private String spec;

	/**
	 * 创建时间
	 */
    @TableField(value = "create_date" ,jdbcType = JdbcType.TIMESTAMP)
    private Date createDate;

	/**
	 * 更新时间
	 */
    @TableField(value = "update_date" ,jdbcType = JdbcType.TIMESTAMP)
    private Date updateDate;

}
