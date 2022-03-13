package com.kangkang.fileLoad.service;

import com.kangkang.fileLoad.entity.Message;
import java.util.List;
import java.util.Map;

/**
 * @InterfaceName: TbAsynLoadService
 * @Author: shaochunhai
 * @Date: 2022/3/12 1:23 下午
 * @Description: TODO
 */
public interface TbAsynLoadService {
    /**
     *  查询任务消息
     * @return
     */
    List<Message> querySqlTask();

    /**
     * 将新建状态修改成处理中
     */
    void updateCreateStatus();

    Map<String, Object> executorSql(String excuteSql,Map<String,Object> p);
}
