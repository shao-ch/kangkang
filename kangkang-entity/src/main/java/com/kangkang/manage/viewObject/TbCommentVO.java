package com.kangkang.manage.viewObject;

import com.kangkang.manage.entity.TbComment;
import lombok.Data;

/**
 * @ClassName: TbCommentVO
 * @Author: shaochunhai
 * @Date: 2021/9/16 11:20 上午
 * @Description: TODO
 */
@Data
public class TbCommentVO extends TbComment {

    //页数
    private Integer pageIndex;

    //每页的数量
    private Integer pageSize;
    //0-代表查询商品评论，1-代表查询回复评论
    private String flag;

}
