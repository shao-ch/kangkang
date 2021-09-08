package com.kangkang.manage.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("tb_category")
public class TbCategory {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 分类名称
	 */
    private String name;

	/**
	 * 级别，0为父节点
	 */
    private String level;

	/**
	 * 上一级别的id
	 */
    private Long parentId;

}
