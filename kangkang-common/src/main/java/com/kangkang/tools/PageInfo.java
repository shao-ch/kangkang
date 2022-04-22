package com.kangkang.tools;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: PageInfo  康康商城的返回前端的分页数据
 * @Author: shaochunhai
 * @Date: 2022/4/21 11:11 下午
 * @Description: TODO
 */
@Data
public class PageInfo<T> {

    private List<T> data;

    private long size;

    private long total;
}
