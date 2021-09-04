package com.kangkang.store.viewObject;

import com.kangkang.manage.entity.TbAddress;
import com.kangkang.manage.entity.TbUser;
import com.kangkang.store.entity.TbOrder;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: CreateOrderVO 创建订单接受参数的类
 * @Author: shaochunhai
 * @Date: 2021/8/26 4:10 下午
 * @Description: TODO
 */
@Data
public class OrderVO extends TbOrder {

    //商品实体的id
    private List<Long> skuIds;

    //用户id
    private Long userId;

    //地址id
    private Long addressId;

    //商品实体数据
    private List<TbSkuVO> tbSkus;

    //用户信息
    private TbUser tbUser;

    //地址信息
    private TbAddress tbAddress;

    /**
     * 防止重复订单状态
     */
    private String repeatOrderFlag;

}
