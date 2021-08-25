package com.kangkang.store.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @ClassName:  TbAfterSaleTemple
 * @Description:  商品售后包装模版表
 * @Author shaochunhai
 * @Date 2021-08-24 17:09:56 
 */
@Data
@TableName("tb_after_sale_temple")
public class TbAfterSaleTemple {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 模版名称
	 */
    private String templeName;

	/**
	 * 父id，最级联查询
	 */
    private Long parentId;

	/**
	 * 模版级别。也就是树形的级别
	 */
    private String level;

}
