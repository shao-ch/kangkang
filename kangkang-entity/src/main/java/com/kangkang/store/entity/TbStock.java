package com.kangkang.store.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @ClassName:  TbStock
 * @Description:  商品库存表
 * @Author shaochunhai
 * @Date 2021-08-25 11:34:58 
 */
@Data
@TableName("tb_stock")
public class TbStock {


	/**
	 * 主键
	 */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

	/**
	 * 商品实体(tb_sku)的主键
	 */
    private Long tbSkuId;

	/**
	 * 库存
	 */
    private Long stock;

}
