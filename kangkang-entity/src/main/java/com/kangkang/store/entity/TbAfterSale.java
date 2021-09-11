package com.kangkang.store.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @ClassName:  TbAfterSale
 * @Description:  
 * @Author shaochunhai
 * @Date 2021-09-11 16:40:37 
 */
@Data
@TableName("tb_after_sale")
public class TbAfterSale {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 分类的id
	 */
    private Long tbCategoryId;

	/**
	 * 售后名称
	 */
    private String name;

	/**
	 * 售后内容
	 */
    private String context;

	/**
	 * 级别，会有子级目录
	 */
    private String level;

	/**
	 * 排序
	 */
    private String order;

	/**
	 * 父节点id，可以为null，如果为空就代表一级目录
	 */
    private String parentId;

}
