package com.kangkang.store.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;


/**
 * @ClassName:  TbDetailTemple
 * @Description:  商品项目模版表
 * @Author shaochunhai
 * @Date 2021-08-24 17:09:56
 */
@Data
@TableName("tb_detail_temple")
public class TbDetailTemple {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 详情模版名称
     */
    private String saleTempleName;

    /**
     * 父节点
     */
    private Long parentId;

    /**
     * 级别
     */
    private String level;

}
