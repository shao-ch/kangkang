package com.kangkang.manage.dtoObject;

import com.kangkang.manage.entity.TbCategory;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: TbCategoryVO
 * @Author: shaochunhai
 * @Date: 2021/9/9 4:12 下午
 * @Description: TODO
 */
@Data
public class TbCategoryDTO {

    /**
     * 0-代表一级目录，1代表其他的子级目录
     */
    private String flag;

    List<TbCategory> categoryList;
}
