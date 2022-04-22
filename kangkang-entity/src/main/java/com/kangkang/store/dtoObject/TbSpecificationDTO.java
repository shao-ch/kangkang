package com.kangkang.store.dtoObject;

import com.kangkang.store.entity.TbSpecification;
import lombok.Data;

/**
 * @ClassName: TbSpecificationVO  规格表接收参数的类
 * @Author: shaochunhai
 * @Date: 2022/4/19 5:00 下午
 * @Description: TODO
 */
@Data
public class TbSpecificationDTO extends TbSpecification {

    //页数
    private Integer pageIndex;

    //每页的数量
    private Integer pageSize;
}
