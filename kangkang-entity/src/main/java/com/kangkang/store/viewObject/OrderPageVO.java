package com.kangkang.store.viewObject;

import com.kangkang.tools.PageUtils;
import lombok.Data;

/**
 * @ClassName: OrderPageVO  order做分页
 * @Author: shaochunhai
 * @Date: 2021/9/2 3:57 下午
 * @Description: TODO
 */
@Data
public class OrderPageVO extends PageUtils {

    /**
     * 用户id
     */
    private String openId;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 查询状态:0-代表查询全部订单，1-代付款订单，2-待收货订单，3-待评价订单
     */
    private String queryOrderFlag;
}
