package com.kangkang.store.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @ClassName:  TbStoreDetail
 * @Description: 商品详情表
 * @Author shaochunhai
 * @Date 2021-08-24 17:14:26 
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
	 * 包装售后模版数据
	 */
    private String afterSaleTemple;

	/**
	 * 商品详情模版数据
	 */
    private String detailTemple;

	/**
	 * 创建时间
	 */
    private java.util.Date createTime;

	/**
	 * 更新时间
	 */
    private java.util.Date updateTime;

}
