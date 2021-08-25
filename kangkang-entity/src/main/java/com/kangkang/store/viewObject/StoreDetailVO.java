package com.kangkang.store.viewObject;

import com.kangkang.store.entity.TbDetailTemple;
import com.kangkang.store.entity.TbStoreDetail;
import lombok.Data;

/**
 * @ClassName: StoreDetailVO   商品详情的响应类
 * @Author: shaochunhai
 * @Date: 2021/8/24 5:12 下午
 * @Description: TODO
 */
@Data
public class StoreDetailVO extends TbStoreDetail {

    //商品详情模版信息   json类型
    private String tbDetailTemple;

    //售后信息模版
    private String tbAfterSalaTemple;

}
