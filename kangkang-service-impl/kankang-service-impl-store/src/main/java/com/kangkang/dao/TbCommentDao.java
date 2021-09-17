package com.kangkang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.manage.entity.TbComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: TbCommentDao
 * @Author: shaochunhai
 * @Date: 2021/9/17 9:49 下午
 * @Description: TODO
 */
@Repository
public interface TbCommentDao extends BaseMapper<TbComment> {
    Page<TbComment> queryCommentInfoByStoreId(Page<TbComment> page,@Param("tbStoreId") Long tbStoreId);

    Page<TbComment> queryReplyCommentInfo(Page<TbComment> page, @Param("revertId")Long revertId, @Param("tbStoreId")Long tbStoreId);

    /**
     * 通过id给赞减1
     * @param id
     */
    void reduceZANById(@Param("id")Long id);
    /**
     * 通过id给赞加1
     * @param id
     */
    void addZANById(@Param("id")Long id);

    /**
     * 通过id查询zan
     * @param id
     * @return
     */
    Integer selectZANById(Long id);
}
