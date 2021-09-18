package com.kangkang.store.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName:  TbStore
 * @Description:  
 * @Author shaochunhai
 * @Date 2021-09-10 16:09:56 
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
	 * 图片路径
	 */
    private String banner;

	/**
	 * 标题
	 */
    private String title;

	/**
	 * 价格，有可能是价格区间
	 */
    private String price;

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
    private Date createDate;

	/**
	 * 更新时间
	 */
    private Date updateDate;

}