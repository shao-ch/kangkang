package com.kangkang.store.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * kangkang商城信息表
 */
@Data
@TableName("tb_store")
public class TbStore {

  //主键
  @TableId(value = "id",type = IdType.AUTO)
  private long id;
  //图片地址
  private String banner;
  //标题
  private String title;
  //价格
  private double price;
  //物流类别
  private String logisticType;

  //产品的模具  是数组
  private String model;

  //产品的规格  是数组
  private String standards;
  //创建时间
  private Date createDate;
  //更新时间
  private Date updateDate;

}
