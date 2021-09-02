package com.kangkang.store.viewObject;

import com.kangkang.tools.PageUtils;
import lombok.Data;

/**
 * @ClassName: OrderPageVO  order做分页
 * @Author: shaochunhai
 * @Date: 2021/9/2 3:57 下午
 * @Description: TODO
 */
@Data
public class OrderPageVO extends PageUtils {

    /**
     * 用户id
     */
    private String openId;
}
