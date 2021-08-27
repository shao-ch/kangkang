package com.kangkang.service.impl;

import com.kangkang.dao.TbAdressDao;
import com.kangkang.dao.TbUserDao;
import com.kangkang.manage.entity.TbAddress;
import com.kangkang.manage.entity.TbUser;
import com.kangkang.service.OrderService;
import com.kangkang.serviceInvoke.InvokingStoreService;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.viewObject.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: OrderServiceImpl
 * @Author: shaochunhai
 * @Date: 2021/8/27 9:19 上午
 * @Description: TODO
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    //远程调用的方法，此微服务掉用另一个微服务
    @Autowired
    private InvokingStoreService invokingStoreService;

    @Autowired
    private TbAdressDao tbAdressDao;
    @Autowired
    private TbUserDao tbUserDao;
    /**
     * 查询订单初始化信息
     * @param order
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public Map<String, Object> queryOrder(OrderVO order) {

        HashMap<String, Object> result = new HashMap<>();
        try {
            OrderVO vo = new OrderVO();
            //查询sku商品信息
            Long skuId = order.getSkuId();

            TbSku sku=invokingStoreService.getSkuById(skuId);
            //如果没有商品实体直接要返回
            if (sku==null){
                result.put("error","无商品实体信息");
                return result;
            }
            vo.setTbSku(sku);
            //查询用户信息
            Long userId = order.getUserId();
            TbUser tbUser = tbUserDao.selectById(userId);
            if (tbUser==null){
                result.put("error","无用户信息");
                return result;
            }
            vo.setTbUser(tbUser);

            //查询 地址信息
            TbAddress tbAddress = tbAdressDao.selectById(order.getAddressId());
            if (tbAddress==null){
                result.put("error","无地址信息");
                return result;
            }

            vo.setTbAddress(tbAddress);

            result.put("order",vo);
            return result;
        } catch (Exception e) {
            log.error("=====查询订单的服务异常=====：【"+ e+"】");
            result.put("error","后台服务异常");
            return result;
        }
    }
}
