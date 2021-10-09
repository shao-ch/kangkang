package com.kangkang.store.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * @ClassName:  TbShoppingCar
 * @Description:  
 * @Author shaochunhai
 * @Date 2021-10-01 11:08:00 
 */
@Data
@TableName("tb_shopping_car")
public class TbShoppingCar {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 商品sku的id
	 */
    @TableField(value = "sku_id" ,jdbcType = JdbcType.BIGINT)
    private Long skuId;

	/**
	 * 人物的标识，唯一的身份认证信息
	 */
    @TableField(value = "open_id" ,jdbcType = JdbcType.VARCHAR)
    private String openId;

	/**
	 * 数量
	 */
    @TableField(value = "count" ,jdbcType = JdbcType.INTEGER)
    private Long count;

	/**
	 * 单个商品的规格型号
	 */
	@TableField(value = "specifications" ,jdbcType = JdbcType.VARCHAR)
	private String specifications;

	/**
	 * 创建时间
	 */
    @TableField(value = "create_time" ,jdbcType = JdbcType.TIMESTAMP,update = "now()")
    private Date createTime;

	/**
	 * 更新时间
	 */
    @TableField(value = "update_time" ,jdbcType = JdbcType.TIMESTAMP,update = "now()")
    private Date updateTime;

}
