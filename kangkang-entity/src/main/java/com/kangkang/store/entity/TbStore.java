package com.kangkang.store.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName:  TbStore
 * @Description:  
 * @Author shaochunhai
 * @Date 2021-09-18 14:01:13 
 */
@Data
@TableName("tb_store")
public class TbStore {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 图片展示
	 */
    private String image;

	/**
	 * 商品详情图片信息
	 */
    private String specification;

	/**
	 * 特有商品公共参数
	 */
    private Object specArgument;

	/**
	 * 分类id，可以查分类，也可以查售后详情
	 */
    private Long tbCategoryId;

	/**
	 * 创建时间
	 */
    private Date createTime;

	/**
	 * 更新时间
	 */
    private Date updateTime;

}
