package com.kangkang.service.impl;
import java.util.*;

import com.kangkang.RedisKeyPrefix;
import com.kangkang.dao.*;
import com.kangkang.manage.entity.TbAddress;
import com.kangkang.manage.entity.TbUser;
import com.kangkang.service.OrderService;
import com.kangkang.serviceInvoke.InvokingStoreService;
import com.kangkang.store.entity.TbOrder;
import com.kangkang.store.entity.TbOrderDetail;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.entity.TbStock;
import com.kangkang.store.viewObject.OrderVO;
import com.kangkang.store.viewObject.TbSkuVO;
import com.kangkang.tools.SnowFlake;
import com.kangkang.untils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: OrderServiceImpl
 * @Author: shaochunhai
 * @Date: 2021/8/27 9:19 上午
 * @Description: TODO
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    //远程调用的方法，此微服务掉用另一个微服务
    @Autowired
    private InvokingStoreService invokingStoreService;

    @Autowired
    private TbAdressDao tbAdressDao;
    @Autowired
    private TbUserDao tbUserDao;

    @Autowired
    private TbOrderDao tbOrderDao;

    @Autowired
    private TbOrderDetailDao tbOrderDetailDao;

    @Autowired
    private TbStockDao tbStockDao;
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
            List<Long> skuIds = order.getSkuIds();

            List<TbSku> skus=invokingStoreService.getSkuById(skuIds);
            //如果没有商品实体直接要返回
            if (skus.isEmpty()){
                result.put("error","无商品实体信息");
                return result;
            }
            //数据转化
            ArrayList<TbSkuVO> tbSkuVOS = getTbSkuVOS(skus);
            vo.setTbSkus(tbSkuVOS);
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

    /**
     * 获取skuVo数据
     * @param skus
     * @return
     */
    private ArrayList<TbSkuVO> getTbSkuVOS(List<TbSku> skus) {
        ArrayList<TbSkuVO> tbSkuVOS = new ArrayList<>();
        for (TbSku tbSku : skus) {
            TbSkuVO tbSkuVO = new TbSkuVO();
            BeanUtils.copyProperties(tbSku,tbSkuVO);
            tbSkuVOS.add(tbSkuVO);
        }
        return tbSkuVOS;
    }


    /**
     * /创建订单的时候没有物流名称和物流单号。
     * @param order
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createOrder(OrderVO order) {
        TbOrder tbOrder = insertOrder(order);
        //然后生成订单向平表
        List<TbSkuVO> tbSkus = order.getTbSkus();
        for (TbSkuVO skuVo : tbSkus) {
            //生成订单详情
            generateOrderDetail(tbOrder, skuVo);
            //这里去redis里面查，如果没有就去数据库查，然后更新缓存。分布式锁
            boolean b = RedisUtils.decrement(redisTemplate, RedisKeyPrefix.STOCK_PREFIX_KEY + skuVo.getId());
            if (!b){
                //如果不存在就要去查询数据库。并且减去1，然后将值保存在redis。然后在日志表中插入一条订单信息
                //1、查询库存数据库
                Integer stock = tbStockDao.queryStockById(skuVo.getId());


                //2、减1之后生成缓存
                RedisUtils.generateRedis(redisTemplate,stock,RedisKeyPrefix.STOCK_LOCK_KEY+skuVo.getId());
            }

            //3、异步生成订单日志

            //4、通过rocketmq做数据库与缓存的同步。这里不需要实时的同步。仅仅是一条通知同步的消息

        }

        
        

    }

    /**
     * 生成订单详情
     * @param tbOrder
     * @param skuVo
     */
    private void generateOrderDetail(TbOrder tbOrder, TbSkuVO skuVo) {
        TbOrderDetail detail = new TbOrderDetail();
        //设置订单id
        detail.setTbOrderId(tbOrder.getId());
        //设置skuid,注意这里是tb_sku表的id
        detail.setSkuId(skuVo.getId());
        //设置发票的id
        detail.setInvoiceId(skuVo.getInvoiceId());
        //设置优惠金额
        detail.setReducePrice(skuVo.getReducePrice());
        //设置邮费
        detail.setPostFee(skuVo.getPostFee());
        //设置评价状态  0-未评价，1-以评价
        detail.setEvaluateStatus(skuVo.getEvaluateStatus());
        detail.setCreateTime(new Date());
        detail.setUpdateTime(new Date());

        tbOrderDetailDao.insert(detail);
    }

    /**
     * 生成订单数据
     * @param order
     * @return
     */
    private TbOrder insertOrder(OrderVO order) {
        TbOrder tbOrder = new TbOrder();
        //创建订单号
        long orderId= SnowFlake.nextId();
        //设置订单号
        tbOrder.setOrderId(orderId);
        //设置地址信息
        tbOrder.setAddressId(order.getAddressId());
        //商品价格
        tbOrder.setGoodPrice(order.getGoodPrice());
        //付款类型
        tbOrder.setPayType(order.getPayType());
        //订单生成时间
        tbOrder.setCreateTime(new Date());
        //买家留言
        tbOrder.setLeaveWord(order.getLeaveWord());
        //点击确认的时候会发送一条付款信息，如果付款成功就给1，否则就给0,前台给的数据
        tbOrder.setOrderStatus(order.getOrderStatus());
        //每一次更新都要给
        tbOrder.setUpdateTime(new Date());

        //生成订单
        tbOrderDao.insert(tbOrder);

        return tbOrder;
    }


}
