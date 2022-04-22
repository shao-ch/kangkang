package com.kangkang.store.dtoObject;

import com.kangkang.tools.PageUtils;
import lombok.Data;

/**
 * @ClassName: StoreSearchVO  首页查询，以及搜索框查询的实体映射
 * @Author: shaochunhai
 * @Date: 2021/12/30 4:49 下午
 * @Description: TODO
 */
@Data
public class StoreSearchDTO extends PageUtils {

    private String searchInfo;

    private String loginName;
}
