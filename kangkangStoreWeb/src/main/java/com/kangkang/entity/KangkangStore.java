package com.kangkang.entity;


import com.kangkang.tools.PageUtils;

import java.util.Date;

/**
 * kangkang商城信息表
 */
public class KangkangStore extends PageUtils {

  //主键
  private long id;
  //图片地址
  private String path;
  //标题
  private String title;
  //价格
  private double price;
  //物流类别
  private String logisticType;
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

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
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
