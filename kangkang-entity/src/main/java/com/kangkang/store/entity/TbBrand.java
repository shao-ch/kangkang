package com.kangkang.store.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @ClassName:  TbBrand
 * @Description:  商品品牌表
 * @Author shaochunhai
 * @Date 2021-08-24 17:09:56
 */
@Data
@TableName("tb_brand")
public class TbBrand {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 品牌代码
     */
    private String brandCode;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌图片
     */
    private String image;

}
