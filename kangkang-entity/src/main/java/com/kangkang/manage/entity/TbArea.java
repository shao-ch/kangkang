package com.kangkang.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName: TbArea
 * @Author: shaochunhai
 * @Date: 2021/8/21 4:25 下午
 * @Description: TODO
 */
@Data
@TableName("tb_area")
public class TbArea {
    @TableId(value = "id",type = IdType.AUTO)
    //主键
    private Long id;
    //区域代码
    private String areaCode;
    //区域名称
    private String areaName;
    //级别：0-省/直辖市，1-市，2-区/县
    private String level;
    //父级别id
    private long parentId;
}
