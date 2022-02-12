package com.kangkang.ESVO;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @ClassName: ESStoreStatisVO  商品统计的文档 (doc)
 * @Author: shaochunhai
 * @Date: 2022/1/3 3:50 下午
 * @Description: TODO
 */
@Data
@Document(indexName = "kangkang_store_statis_info",type = "doc")
public class ESStoreStatisVO {

    /**
     * 索引id
     */
    @Id
    private Long id;


    /**
     * 图片展示
     */
    @Field(type = FieldType.Text)
    private String image;

    /**
     * 商品详情图片信息
     */
    @Field(type = FieldType.Text)
    private String specification;

    /**
     * 特有商品公共参数
     */
    @Field(type = FieldType.Object)
    private Object specArgument;

    /**
     * 分类id，可以查分类，也可以查售后详情
     */
    @Field(type = FieldType.Text)
    private Long tbCategoryId;


    /**
     * 访问量，默认是0
     */
    @Field(type = FieldType.Long)
    private Long visitCount=0L;


}
