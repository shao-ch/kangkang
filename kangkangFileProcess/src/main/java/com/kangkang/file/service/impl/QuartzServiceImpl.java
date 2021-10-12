package com.kangkang.file.service.impl;

import com.kangkang.file.dao.TbQuartzDao;
import com.kangkang.file.dao.TbSignDao;
import com.kangkang.file.dao.TbUserDao;
import com.kangkang.file.service.QuartzService;
import com.kangkang.manage.entity.TbQuartz;
import com.kangkang.manage.entity.TbSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: QuartzServiceImpl
 * @Author: shaochunhai
 * @Date: 2021/10/12 2:22 下午
 * @Description: TODO
 */
@Service
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private TbUserDao tbUserDao;


    @Autowired
    private TbSignDao tbSignDao;

    @Autowired
    private TbQuartzDao tbQuartzDao;



    /**
     * 查询所有用户的openid
     * @return
     */
    @Override
    public List<String> queryAllUser() {
        return tbUserDao.queryAllUser();
    }


    /**
     * 保存签到信息
     * @param tbSigns
     */
    @Override
    public void insertTbSign(List<TbSign> tbSigns) {
        tbSignDao.insertBatchSomeColumn(tbSigns);
    }

    @Override
    public List<TbQuartz> selectQuartzInfo() {
        return tbQuartzDao.selectAll();
    }
}
