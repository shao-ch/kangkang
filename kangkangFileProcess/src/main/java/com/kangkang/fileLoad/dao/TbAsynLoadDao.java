package com.kangkang.fileLoad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kangkang.fileLoad.entity.Message;
import com.kangkang.manage.entity.TbAsynLoadControl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName: TbAsynLoadDao
 * @Author: shaochunhai
 * @Date: 2022/3/12 1:25 下午
 * @Description: TODO
 */
@Repository
public interface TbAsynLoadDao extends BaseMapper<TbAsynLoadControl> {

    /**
     * 查询任务消息
     * @return
     */
    List<Message> querySqlTask();

    void updateCreateStatus();

    /**
     * 执行自定义sql
     * @param excuteSql  需要执行的sql
     * @param p  这是参数
     * @return
     */
    Map<String, Object> executorSql(@Param("sql") String excuteSql, @Param("p") Map<String, Object> params);

    /**
     * 通过id将完成的数据更新为已完成
     * @param id
     */
    void updateStatusToSuccess(@Param("id") Long id);
}
