package com.kangkang.ESVO;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @ClassName: ESStoreVO  商品信息的映射类，索引为kangkang_store_info
 * @Author: shaochunhai
 * @Date: 2021/12/22 1:19 下午
 * @Description: TODO
 */
@Data
@Document(indexName = "kangkang_store_info",type = "doc")
public class ESStoreVO {

    /**
     * 商品id，也就是sku表中的id
     */
    @Id
    private Long skuId;

    /**
     * store的id，也就是一类商品的id
     */
    @Field(type = FieldType.Integer)
    private Long storeId;

    /**
     * 图片展示
     */
    @Field(type = FieldType.Text)
    private String storeImage;

    /**
     * 商品详情图片信息
     */
    @Field(type = FieldType.Text)
    private String storeSpecification;

    /**
     * 特有商品公共参数
     */
    @Field(type = FieldType.Object)
    private String StoreSpecArgument;


    /**
     * 标题
     * ik_max_word:会将文本做最细粒度的拆分，比如会将“中华人民共和国人民大会堂”拆分为
     * “中华人民共和国、中华人民、中华、华人、人民共和国、人民、共和国、大会堂、大会、会堂等词语。
     */
    @Field(type = FieldType.Keyword,analyzer = "ik_max_word")
    private String skuTitle;

    /**
     * 图片
     */
    @Field(type = FieldType.Text)
    private String skuImage;

    /**
     * 价格
     */
    @Field(type = FieldType.Float)
    private Double skuPrice;

    /**
     * 商品实体的规格参数，内存，颜色，，购买方式等   json 要使用FieldType.Object 这个类型，会有层级关系
     */
    @Field(type = FieldType.Object)
    private String skuSpecParams;

    /**
     * 状态：0-可购买，1-购买状态
     */
    @Field(type = FieldType.Text)
    private String status;




}
