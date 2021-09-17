package com.kangkang.manage.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_comment")
public class TbComment {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 商品的主键
	 */
    private Long tbStoreId;

	/**
	 * 用户id
	 */
    private Long userId;

	/**
	 * 好评：null为不打分，0-代表最差，1-一颗星，2-两颗星...最高5颗星
	 */
    private String commentLevel;

	/**
	 * 商品信息，在评论里展示你买的什么商品
	 */
    private String storeInfo;

	/**
	 * 点赞数
	 */
    private Long zan;

	/**
	 * 评论内容
	 */
    private String context;

	/**
	 * 回复人的id，也就是本张表的id
	 */
    private Long revertId;

	/**
	 * 买家秀信息
	 */
    private String buyerShowInfo;

	/**
	 * 评论时间
	 */
    private Date createTime;

}
