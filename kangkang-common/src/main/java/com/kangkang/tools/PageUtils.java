package com.kangkang.tools;

/**
 * @ClassName: PageUtils
 * @Author: shaochunhai
 * @Date: 2021/8/12 10:11 上午
 * @Description: TODO
 */
public class PageUtils {

    //页数
    private Integer pageIndex;

    //每页的数量
    private Integer pageSize;

    public Integer getPageIndex() {
        return pageIndex;
    }
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
