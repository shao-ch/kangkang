package com.kangkang.store.viewObject;

import com.kangkang.store.entity.TbShoppingCar;
import lombok.Data;

/**
 * @ClassName: TbShoppingVO
 * @Author: shaochunhai
 * @Date: 2021/10/9 3:18 下午
 * @Description: TODO
 */
@Data
public class TbShoppingVO extends TbShoppingCar {

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String image;

    /**
     * 价格
     */
    private Double price;



    /**
     * 商品状态
     */
    private Double goodStatus;

}
