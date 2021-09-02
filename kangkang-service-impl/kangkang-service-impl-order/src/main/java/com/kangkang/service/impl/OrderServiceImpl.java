package com.kangkang.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.dao.*;
import com.kangkang.enumInfo.RedisKeyPrefix;
import com.kangkang.enumInfo.RocketInfo;
import com.kangkang.manage.entity.TbAddress;
import com.kangkang.manage.entity.TbUser;
import com.kangkang.service.OrderService;
import com.kangkang.serviceInvoke.InvokingStoreService;
import com.kangkang.store.entity.TbOrder;
import com.kangkang.store.entity.TbOrderDetail;
import com.kangkang.store.entity.TbSku;
import com.kangkang.store.entity.TbStock;
import com.kangkang.store.viewObject.OrderPageVO;
import com.kangkang.store.viewObject.OrderVO;
import com.kangkang.store.viewObject.TbSkuVO;
import com.kangkang.tools.SnowFlake;
import com.kangkang.untils.MqUtils;
import com.kangkang.untils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private RedisTemplate<String, Object> redisTemplate;
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

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    /**
     * 查询订单初始化信息
     *
     * @param order
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Map<String, Object> queryOrder(OrderVO order) {

        HashMap<String, Object> result = new HashMap<>();
        try {
            OrderVO vo = new OrderVO();
            //查询sku商品信息
            List<Long> skuIds = order.getSkuIds();

            List<TbSku> skus = invokingStoreService.getSkuById(skuIds);
            //如果没有商品实体直接要返回
            if (skus.isEmpty()) {
                result.put("error", "无商品实体信息");
                return result;
            }
            //数据转化
            ArrayList<TbSkuVO> tbSkuVOS = getTbSkuVOS(skus);
            vo.setTbSkus(tbSkuVOS);
            //查询用户信息
            Long userId = order.getUserId();
            TbUser tbUser = tbUserDao.selectById(userId);
            if (tbUser == null) {
                result.put("error", "无用户信息");
                return result;
            }
            vo.setTbUser(tbUser);

            //查询 地址信息
            TbAddress tbAddress = tbAdressDao.selectById(order.getAddressId());
            if (tbAddress == null) {
                result.put("error", "无地址信息");
                return result;
            }

            vo.setTbAddress(tbAddress);

            result.put("order", vo);
            return result;
        } catch (Exception e) {
            log.error("=====查询订单的服务异常=====：【" + e + "】");
            result.put("error", "后台服务异常");
            return result;
        }
    }

    /**
     * 获取skuVo数据
     *
     * @param skus
     * @return
     */
    private ArrayList<TbSkuVO> getTbSkuVOS(List<TbSku> skus) {
        ArrayList<TbSkuVO> tbSkuVOS = new ArrayList<>();
        for (TbSku tbSku : skus) {
            TbSkuVO tbSkuVO = new TbSkuVO();
            BeanUtils.copyProperties(tbSku, tbSkuVO);
            tbSkuVOS.add(tbSkuVO);
        }
        return tbSkuVOS;
    }


    /**
     * /创建订单的时候没有物流名称和物流单号。
     *
     * @param order
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TbOrder createOrder(OrderVO order) {
        //定义一个flag变量来判断是否已经扣减库存了
        int flag = 0;
        //这个容器是里面已经成功多少订单了，都要做回滚操作
        ArrayList<Long> orderIds = new ArrayList<>();
        TbOrder tbOrder = insertOrder(order);
        //然后生成订单向平表
        List<TbSkuVO> tbSkus = order.getTbSkus();
        for (TbSkuVO skuVo : tbSkus) {
            //重置状态
            flag = 0;
            try {
                //这里去redis里面查，如果没有就去数据库查，然后更新缓存。分布式锁   --先去扣减库存，成功了在进行订单的生成
                boolean b = RedisUtils.decrement(redisTemplate, RedisKeyPrefix.STOCK_PREFIX_KEY + skuVo.getId());
                if (!b) {
                    //获取分布式锁信息
                    Boolean lock = redisTemplate.opsForValue().setIfAbsent(RedisKeyPrefix.STOCK_LOCK_KEY + skuVo.getId(), 0);
                    //如果获取锁成功就去redis生成库存数据
                    if (lock) {
                        //获取锁成功就将缓存更新
                        TbStock tbStock = tbStockDao.queryStockById(skuVo.getId());
                        redisTemplate.opsForValue().set(RedisKeyPrefix.STOCK_PREFIX_KEY + skuVo.getId(), tbStock.getStock() - 1);
                        //缓存设置成功之后要将锁加1；
                        redisTemplate.opsForValue().increment(RedisKeyPrefix.STOCK_LOCK_KEY + skuVo.getId());
                        flag += 1;
                    } else {
                        //这里做一次查询，如果还是没有缓存,就去更新数据库
                        if (redisTemplate.opsForValue().get(RedisKeyPrefix.STOCK_PREFIX_KEY + skuVo.getId()) == null) {
                            tbStockDao.updateStockById(skuVo.getId());
                        } else {
                            RedisUtils.decrement(redisTemplate, RedisKeyPrefix.STOCK_PREFIX_KEY + skuVo.getId());
                        }
                        flag += 1;
                    }
                } else {
                    flag += 1;
                }

                //生成订单详情
                generateOrderDetail(tbOrder, skuVo);

                //3、异步生成订单日志
                MqUtils.send(defaultMQProducer, RocketInfo.SEND_LOG_TOPIC, RocketInfo.SEND_LOG_TAG, "");
                //4、通过rocketmq做数据库与缓存的同步。这里不需要实时的同步。仅仅是一条通知同步的消息
                MqUtils.send(defaultMQProducer, RocketInfo.SEND_ORDER_TOPIC, RocketInfo.SEND_ORDER_TAG, String.valueOf(skuVo.getId()));
                //5、将成功的订单存入容器中
                orderIds.add(skuVo.getId());
            } catch (Exception e) {
                log.error("=====订单生成失败=====：【" + e + "】");
                //判断是否已经扣减了库存，如果是就要在加回去
                if (flag == 1) {
                    //1、判断是容器中是否存在，不存在添加进去
                    if (!orderIds.contains(skuVo.getId())) {
                        orderIds.add(skuVo.getId());
                    }

                    //循环进行回滚
                    for (Long orderId : orderIds) {
                        RedisUtils.increment(redisTemplate, RedisKeyPrefix.STOCK_PREFIX_KEY + orderId);
                        //删除订单
                        tbOrderDao.deleteById(orderId);
                    }

                }
                return null;
            }
        }

        return order;
    }

    /**
     * 查询全部订单列表
     * @param order
     * @return
     */
    @Override
    public Page<Map<String, Object>> queryOrderList(OrderPageVO order) {
        Page<Map<String, Object>> page = new Page<>(order.getPageIndex(), order.getPageSize());
        //通过openid查询所有订单消息，并返回数据。要分页

        Page<Map<String, Object>> result = tbOrderDao.queryInfoByOpenId(page,order.getOpenId());

        return result;
    }

    /**
     * 生成订单详情
     *
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
     *
     * @param order
     * @return
     */
    private TbOrder insertOrder(OrderVO order) {
        TbOrder tbOrder = new TbOrder();
        //设置用户id
        tbOrder.setOpenId(order.getOpenId());
        //创建订单号
        long orderId = SnowFlake.nextId();
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
