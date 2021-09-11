package com.kangkang.store.viewObject;

import com.kangkang.store.entity.TbOrder;
import com.kangkang.store.entity.TbOrderDetail;
import com.kangkang.store.entity.TbSku;
import lombok.Data;

/**
 * @ClassName: OrderView  返回给前端的订单数据
 * @Author: shaochunhai
 * @Date: 2021/9/11 5:18 下午
 * @Description: TODO
 */
@Data
public class OrderView {
    /**
     * 订单数据
     */
    private TbOrder tbOrder;

    /**
     * 订单详情数据
     */
    private TbOrderDetail tbOrderDetail;

    /**
     * 尸体数据
     */
    private TbSku tbSku;
}
