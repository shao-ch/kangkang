package com.kangkang.store.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName:  TbOrderDetail  订单详情表
 * @Description:  
 * @Author shaochunhai
 * @Date 2021-08-29 16:02:14 
 */
@Data
@TableName("tb_order_detail")
public class TbOrderDetail {


	/**
	 * 订单详情表的主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 订单表的主键
	 */
    private Long tbOrderId;

	/**
	 * 商品实体表的主键的id
	 */
    private Long skuId;

	/**
	 * 发票表的主键
	 */
    private Long invoiceId;

	/**
	 * 商品的优惠金额
	 */
    private Double reducePrice;

	/**
	 * 邮费
	 */
    private Double postFee;

	/**
	 * 发货时间
	 */
    private java.util.Date deliverTime;

	/**
	 * 物流名称
	 */
    private String logisticsName;

	/**
	 * 物流单号
	 */
    private String logisticsCode;

	/**
	 * 是否以评价：0未评价，1以评价
	 */
    private String evaluateStatus;

	/**
	 * 创建时间
	 */
    private Date createTime;

	/**
	 * 更新时间
	 */
    private Date updateTime;

}
