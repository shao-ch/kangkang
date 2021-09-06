package com.kangkang.store.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @ClassName:  TbStore
 * @Description:  
 * @Author shaochunhai
 * @Date 2021-09-06 16:56:09 
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
	 * 售后模版id
	 */
    private Long afterSaleTempleId;

	/**
	 * 详情模版id
	 */
    private Long detailTempleId;

	/**
	 * 图片路径
	 */
    private String banner;

	/**
	 * 标题
	 */
    private String title;

	/**
	 * 物流类别
	 */
    private String logisticType;

	/**
	 * 是否在售卖；0-不可售卖，1-可售卖
	 */
    private String issolding;

	/**
	 * 是否有效。0-有效，1无效
	 */
    private String status;

	/**
	 * 创建时间
	 */
    private java.util.Date createDate;

	/**
	 * 更新时间
	 */
    private java.util.Date updateDate;

}
