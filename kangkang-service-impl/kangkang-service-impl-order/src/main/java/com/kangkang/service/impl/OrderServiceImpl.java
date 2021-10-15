package com.kangkang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kangkang.dao.*;
import com.kangkang.enumInfo.RedisKeyPrefix;
import com.kangkang.enumInfo.RocketInfo;
import com.kangkang.manage.entity.TbAddress;
import com.kangkang.manage.entity.TbUser;
import com.kangkang.service.OrderService;
import com.kangkang.serviceInvoke.InvokingStoreService;
import com.kangkang.store.entity.*;
import com.kangkang.store.viewObject.*;
import com.kangkang.tools.ResultUtils;
import com.kangkang.tools.SnowFlake;
import com.kangkang.untils.MqUtils;
import com.kangkang.untils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
    private RedisTemplate redisTemplate;
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
    private TbShoppingCarDao tbShoppingCarDao;
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> createOrder(OrderVO order) {
        HashMap<String, Object> result = new HashMap<>();
//        Boolean repeat = redisTemplate.opsForValue().setIfAbsent(order.getRepeatOrderFlag(), 1);
//        if (!repeat) {
//            result.put("status", "error");
//            result.put("data", "订单不能重复");
//            return result;
//        }

        //定义一个flag变量来判断是否已经扣减库存了
        int flag = 0, lockStatus = 0;
        //这个容器是里面已经成功多少订单了，都要做回滚操作
        ArrayList<Long> orderIds = new ArrayList<>();
        ArrayList<String> lockKey = new ArrayList<>();
        TbOrder tbOrder = insertOrder(order);
        //然后生成订单向平表
        List<TbSkuVO> tbSkus = order.getTbSkus();
        for (TbSkuVO skuVo : tbSkus) {
            //重置状态
            flag = 0;
            lockStatus = 0;
            try {
                //这里去redis里面查，如果没有就去数据库查，然后更新缓存。分布式锁   --先去扣减库存，成功了在进行订单的生成
                boolean b = RedisUtils.decrement(redisTemplate, RedisKeyPrefix.STOCK_PREFIX_KEY + skuVo.getId());
                if (!b) {
                    //获取分布式锁信息
                    Boolean lock = redisTemplate.opsForValue().setIfAbsent(RedisKeyPrefix.STOCK_LOCK_KEY + skuVo.getId(), 0);
                    //将锁放入容器中，如果失败要回滚
                    lockKey.add(RedisKeyPrefix.STOCK_LOCK_KEY + skuVo.getId());
                    lockStatus += 1;
                    //如果获取锁成功就去redis生成库存数据
                    synchronized (this) {   //这里要进行加锁处理，防止出现脏读问题
                        if (lock) {
                            //获取锁成功就将缓存更新
                            Integer stock = tbStockDao.queryStockById(skuVo.getId());
                            redisTemplate.opsForValue().set(RedisKeyPrefix.STOCK_PREFIX_KEY + skuVo.getId(), stock);
                            flag += 1;
                        } else {
                            //这里做一次查询，如果还是没有缓存,就去更新数据库
                            if (redisTemplate.opsForValue().get(RedisKeyPrefix.STOCK_PREFIX_KEY + skuVo.getId()) == null) {
                                tbStockDao.updateStockById(skuVo.getId());
                            }
                            flag += 1;
                        }
                    }
                } else {
                    flag += 1;
                }

                //生成订单详情
                generateOrderDetail(tbOrder, skuVo);

                log.info("=====开始发送ocketmq订单日志信息====");
                //3、异步生成订单日志
                MqUtils.send(defaultMQProducer, RocketInfo.SEND_LOG_TOPIC, RocketInfo.SEND_LOG_TAG, "订单日志信息");
                log.info("=====发送ocketmq订单日志信息====：[success]");
                //4、通过rocketmq做数据库与缓存的同步。这里不需要实时的同步。仅仅是一条通知同步的消息
                MqUtils.send(defaultMQProducer, RocketInfo.SEND_ORDER_TOPIC, RocketInfo.SEND_ORDER_TAG, String.valueOf(skuVo.getId()));
                log.info("=====发送rocketmq消息同步缓存和数据库====：[success]");

                //5、将成功的订单存入容器中
                orderIds.add(skuVo.getId());

                //6、此时把锁删除，否则别人一直去操作数据库
                if (1 == lockStatus) {
                    redisTemplate.delete(RedisKeyPrefix.STOCK_LOCK_KEY + skuVo.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("=====订单生成失败=====：【" + e + "】");
                //判断是否已经扣减了库存并且容器中不存在，要添加进去
                if (flag == 1 && !orderIds.contains(skuVo.getId())) {
                    orderIds.add(skuVo.getId());
                }
                //循环删除锁
                for (String key : lockKey) {
                    redisTemplate.delete(key);
                }
                //循环进行回滚
                for (Long orderId : orderIds) {
                    RedisUtils.increment(redisTemplate, RedisKeyPrefix.STOCK_PREFIX_KEY + orderId);
                    //删除订单
                    tbOrderDao.deleteById(orderId);
                }
                //删除重复消费的标志
                redisTemplate.delete(order.getRepeatOrderFlag());

                //这里要手动回滚事务，不然插入操作就会执行的
                throw new RuntimeException("订单生成失败");
            }
        }
        result.put("status", "success");
        result.put("data", order);
        //此时将key存入redis，作为防止重复下订单的操作
        redisTemplate.opsForValue().set(order.getRepeatOrderFlag(), 1);
        return result;
    }


    /**
     * 查询代付款订单
     *
     * @param orderPageVO
     * @return
     */
    @Override
    public Page<OrderView> queryPayingOrder(OrderPageVO orderPageVO) {
        ArrayList<OrderView> result = new ArrayList<>();
        //第一步查询所有代付款订单
        Page<OrderView> page = new Page<>(orderPageVO.getPageIndex(), orderPageVO.getPageSize());

        //查询订单数据
        Page<TbOrder> tbOrders = tbOrderDao.selectPayingOrder(new Page<>(orderPageVO.getPageIndex(), orderPageVO.getPageSize()),
                orderPageVO.getOpenId(), orderPageVO.getOrderStatus());
        //数据的转入
        BeanUtils.copyProperties(tbOrders, page);
        if (tbOrders.getRecords().isEmpty()) {
            return page;
        }
        //获取其他的详情数据
        for (TbOrder tbOrder : tbOrders.getRecords()) {
            OrderView orderView = new OrderView();
            //存入订单数据
            orderView.setTbOrder(tbOrder);
            //存入订单详情数据
            TbOrderDetail detail = tbOrderDetailDao.selectOne(new QueryWrapper<TbOrderDetail>().eq("tb_order_id", tbOrder.getId()));
            orderView.setTbOrderDetail(detail);
            //存入商品数据
            TbSku tbSku = tbOrderDao.selectSkuInfo(detail.getSkuId());
            orderView.setTbSku(tbSku);

            result.add(orderView);
        }
        page.setRecords(result);

        //返回的信息有：sku 的图片，skuid
        return page;
    }

    /**
     * 查询订单列表  0-代表查询全部订单，1-代付款订单，2-待收货订单，3-待评价订单
     *
     * @param order
     * @return
     */
    @Override
    public Page<Map<String, Object>> queryOrderList(OrderPageVO order) {
        Page<Map<String, Object>> page = new Page<>(order.getPageIndex(), order.getPageSize());
        //获取查询状态，也就是查询什么订单
        String flag = order.getQueryOrderFlag();


        Page<Map<String, Object>> result = tbOrderDao.queryInfoByOpenId(page, order.getOpenId(), flag);

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
        //商品状态
        detail.setGoodStatus(skuVo.getGoodStatus());
        //设置邮费
        detail.setPostFee(skuVo.getPostFee());
        //设置评价状态  0-未评价，1-以评价  初始创建订单默认是未评价
        detail.setEvaluateStatus("0");
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
        Long orderId = SnowFlake.nextId();
        //设置订单号
        tbOrder.setOrderId(String.valueOf(orderId));
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
        //点击确认的时候会发送一条付款信息，如果付款成功就给0，否则就给1,前台给的数据
        tbOrder.setOrderStatus(order.getOrderStatus());
        //每一次更新都要给
        tbOrder.setUpdateTime(new Date());

        //生成订单
        tbOrderDao.insert(tbOrder);

        return tbOrder;
    }


    /**
     * 添加购物车
     *
     * @param shoppingCar isolation  事务的隔离级别
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public void addShoppingCar(TbShoppingCar shoppingCar) {

        tbShoppingCarDao.insert(shoppingCar);
    }

    /**
     * 查询购物车数量
     *
     * @param openId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Integer getShopCarCount(String openId) {

        QueryWrapper<TbShoppingCar> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openId);
        return tbShoppingCarDao.selectCount(wrapper);
    }

    /**
     * 删除购物车信息
     *
     * @param cars
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public void deleteShoppingCar(List<Long> cars) {
        tbShoppingCarDao.deleteBatchIds(cars);
    }


    /**
     * 查询购物车内容
     *
     * @param openId
     * @return
     */
    @Override
    public List<TbShoppingVO> queryShoppingCar(String openId) {
//        ArrayList<TbShoppingVO> list = new ArrayList<>();
//        List<Long> skuIds = tbShoppingCarDao.selectSkuIds(openId);
//        if (skuIds.isEmpty()) {
//            return list;
//        }
        //查询sku商品集合信息
        return tbShoppingCarDao.selectShoppingInfo(openId);
    }


    /**
     * 查询订单数量    0-表示全部订单的数量，1-待付款订单数量，2-待收货订单数量，3-待评价订单数量
     *
     * @param openId
     * @return
     */
    @Override
    public ResultUtils<Map<String, Object>> queryOrderCount(String openId) {

        HashMap<String, Object> map = new HashMap<>();
        try {
            //查询全部订单
            Integer allCount = tbOrderDao.selectAllOrderCount(openId, "0");
            map.put("0", allCount);
            //没有订单
            if (allCount == 0) {
                map.put("1", 0);
                map.put("2", 0);
                map.put("3", 0);
                return ResultUtils.result(map, "0", null);
            }
            //查询待付款订单数量
            Integer noPay = tbOrderDao.selectAllOrderCount(openId, "1");
            //查询待收货订单数量
            Integer noReceive = tbOrderDao.selectAllOrderCount(openId, "2");
            //查询待评价订单数量
            Integer noComment = tbOrderDao.selectAllOrderCount(openId, "3");

            map.put("1", noPay);
            map.put("2", noReceive);
            map.put("3", noComment);
        } catch (Exception e) {
            log.error("=====查询订单数量=====：【" + e + "】");
            return ResultUtils.result(map, "1", "查询订单数量失败：【"+e.getMessage()+"】");
        }
        return ResultUtils.result(map, "0", null);
    }
}
