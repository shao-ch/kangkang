package com.kangkang.store.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName:  TbOrder 商品订单表
 * @Description:  
 * @Author shaochunhai
 * @Date 2021-08-29 16:01:26 
 */
@Data
@TableName("tb_order")
public class TbOrder {


	/**
	 * 订单主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 由于购物车生成的订单号一样，所以有可能重复
	 */
    private Long orderId;

	/**
	 * tb_address的物理主键，也就是收货人信息
	 */
    private Long addressId;

	/**
	 * 订单总价格
	 */
    private Double goodPrice;

	/**
	 * 支付类型
	 */
    private String payType;

	/**
	 * 订单创建时间
	 */
    private Date createTime;

	/**
	 * 订单付款时间
	 */
    private Date payTime;

	/**
	 * 买家留言
	 */
    private String leaveWord;

	/**
	 * 订单状态：0未付款，1已付款
	 */
    private String orderStatus;

	/**
	 * 更新时间
	 */
    private Date updateTime;

}
