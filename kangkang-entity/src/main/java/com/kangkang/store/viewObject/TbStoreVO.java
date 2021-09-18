package com.kangkang.store.viewObject;

import com.kangkang.store.entity.TbAfterSale;
import com.kangkang.store.entity.TbStore;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: StoreDetailVO   商品详情的响应类
 * @Author: shaochunhai
 * @Date: 2021/8/24 5:12 下午
 * @Description: TODO
 */
@Data
public class TbStoreVO extends TbStore {

    //售后信息模版
    private List<TbAfterSale> tbAfterSales;

    private Long skuId;

    private Double price;

    private String title;

}
