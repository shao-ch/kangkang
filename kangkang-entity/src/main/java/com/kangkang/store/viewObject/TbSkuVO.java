package com.kangkang.store.viewObject;

import com.kangkang.store.entity.TbSku;
import lombok.Data;

/**
 * @ClassName: TbSkuVO
 * @Author: shaochunhai
 * @Date: 2021/8/29 4:12 下午
 * @Description: TODO
 */
@Data
public class TbSkuVO extends TbSku {

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
     * 物流名称
     */
    private String logisticsName;

    /**
     * 是否以评价：0未评价，1以评价
     */
    private String evaluateStatus;

    /**
     * 商品状态，0-生成订单，1-有物流单号，3-已发货
     */
    private String goodStatus;
}
