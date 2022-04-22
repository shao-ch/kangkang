package com.kangkang.store.voObject;

import com.kangkang.annotation.common.FieldConvert;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TbSpecificationVO  规格表与前台的交互类
 * @Author: shaochunhai
 * @Date: 2022/4/19 5:00 下午
 * @Description: TODO
 */
@Data
public class TbSpecificationVO {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 规格名称
     */
    private String ruleName;

    /**
     * 规格属性名称
     */
    private String attrName;


    /**
     * 规格属性值
     */
    private String[] attrValue;


    /**
     * 规格属性映射，map的json格式
     * [{"inputVisible":false,"inputValue":"","detail":["啊"],"value":"啊"}]
     */
    @FieldConvert(parmName = {"inputVisible","inputValue","detail","value"},
    paramsType = {String.class,String.class,String[].class,String.class})
    private List<Map<String,Object>> spec;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

}
