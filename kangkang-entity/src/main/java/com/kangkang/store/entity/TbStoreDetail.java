package com.kangkang.store.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName:  TbStoreDetail
 * @Description:  
 * @Author shaochunhai
 * @Date 2021-09-10 16:11:40 
 */
@Data
@TableName("tb_store_detail")
public class TbStoreDetail {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * tb_store的主键id
	 */
    private Long sid;

	/**
	 * 图片展示
	 */
    private String image;

	/**
	 * 商品的全部规格数据
	 */
    private String specification;

	/**
	 * 特有参数数据，比如内存，颜色等。
	 */
    private String specArgument;

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
