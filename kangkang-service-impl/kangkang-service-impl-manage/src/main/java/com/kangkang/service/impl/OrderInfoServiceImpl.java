package com.kangkang.service.impl;

import com.kangkang.dao.TbStockManageDao;
import com.kangkang.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: OrderInfoServiceImpl
 * @Author: shaochunhai
 * @Date: 2021/9/6 3:24 下午
 * @Description: TODO
 */
@Service
public class OrderInfoServiceImpl  implements OrderInfoService {

    @Autowired
    private TbStockManageDao tbStockManageDao;


    @Override
    public void updateStockBySkuId(String msg) {
        tbStockManageDao.updateStockBySkuId(Long.valueOf(msg));
    }
}
