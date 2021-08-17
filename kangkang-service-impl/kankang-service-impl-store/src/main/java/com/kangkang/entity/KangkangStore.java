package com.kangkang.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * kangkang商城信息表
 */
@TableName("tb_store")
public class KangkangStore {

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

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getBanner() {
    return banner;
  }

  public void setBanner(String banner) {
    this.banner = banner;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getLogisticType() {
    return logisticType;
  }

  public void setLogisticType(String logisticType) {
    this.logisticType = logisticType;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getStandards() {
    return standards;
  }

  public void setStandards(String standards) {
    this.standards = standards;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
}
