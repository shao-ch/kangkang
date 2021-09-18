package com.kangkang.store.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @ClassName:  TbSku
 * @Description:  商品实体表
 * @Author shaochunhai
 * @Date 2021-08-24 17:08:42 
 */
@Data
@TableName("tb_sku")
public class TbSku {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 商品主表的主键
	 */
    private Long tbStoreId;

	/**
	 * 标题
	 */
    private String title;

	/**
	 * 图片
	 */
    private String image;

	/**
	 * 价格
	 */
    private Double price;

	/**
	 * 商品实体的规格参数，内存，颜色，，购买方式等
	 */
    private Object specParams;

	/**
	 * 状态：0-可购买，1-购买状态
	 */
    private String status;

	/**
	 * 创建时间
	 */
    private java.util.Date createTime;

	/**
	 * 更新时间
	 */
    private java.util.Date updateTime;

}
