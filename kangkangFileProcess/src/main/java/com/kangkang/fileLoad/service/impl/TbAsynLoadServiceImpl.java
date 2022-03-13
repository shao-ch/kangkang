package com.kangkang.fileLoad.service.impl;

import com.kangkang.fileLoad.dao.TbAsynLoadDao;
import com.kangkang.fileLoad.entity.Message;
import com.kangkang.fileLoad.service.TbAsynLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: TbAsynLoadService  异步处理的服务类
 * @Author: shaochunhai
 * @Date: 2022/3/12 1:24 下午
 * @Description: TODO
 */
@Service
public class TbAsynLoadServiceImpl implements TbAsynLoadService {

    @Autowired
    private TbAsynLoadDao tbAsynLoadDao;

    /**
     * 查询sql的任务
     * @return
     */
    @Override
    public List<Message> querySqlTask() {
        return tbAsynLoadDao.querySqlTask();
    }

    /**
     * 将创建状态修改为处理中
     */
    @Override
    public void updateCreateStatus() {
         tbAsynLoadDao.updateCreateStatus();
    }

    @Override
    public Map<String, Object> executorSql(String excuteSql, Map<String, Object> params) {
        return   tbAsynLoadDao.executorSql(excuteSql,params);
    }
}
