package com.kangkang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kangkang.dao.KangKangUserDao;
import com.kangkang.dao.TbAddressDao;
import com.kangkang.manage.entity.TbUser;
import com.kangkang.manage.entity.TbAddress;
import com.kangkang.manage.dtoObject.TbAdressDTO;
import com.kangkang.service.ManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ManageServiceImpl
 * @Author: shaochunhai
 * @Date: 2021/8/8 3:56 下午
 * @Description: TODO
 */
@Slf4j
@Service("manageService")
public class ManageServiceImpl implements ManageService {


    @Autowired
    private KangKangUserDao kangKangUserDao;

    @Autowired
    private TbAddressDao tbAddressDao;


    /**
     * 注册账号
     *
     * @param tbUser
     */
    @Override
    @Transactional
    public void save(TbUser tbUser) {

        kangKangUserDao.insert(tbUser);
    }

    /**
     * 通过openid查询用户
     *
     * @param tbUser
     * @return
     */
    @Override
    public TbUser selectUser(TbUser tbUser) {

        QueryWrapper<TbUser> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", tbUser.getOpenid());
        return kangKangUserDao.selectOne(wrapper);
    }


    /**
     * 通过openId查询用户地址
     *
     * @param openId openId
     * @return
     */
    @Override
    public List<TbAddress> selectAddress(String openId) {

        //查询所有收货地址
        List<TbAddress> address = tbAddressDao.selectAddress(openId);

        if (address.isEmpty()) {
            return address;
        }

        return address;
    }


    /**
     * 新增收货地址
     *
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> commitAddress(TbAdressDTO vo) {

        HashMap<String, Object> result = new HashMap<>();
        try {
            //设置默认地址
            if (StringUtils.equals(vo.getIsDefault(), "1")) {
                //将所有地址更新为1
                tbAddressDao.updateIsDefaultByUserId(vo.getOpenId(), '0');
            }

            TbAddress tbAddress = new TbAddress();
            //数据转换
            BeanUtils.copyProperties(vo, tbAddress);


            //如果是更新操作id是不为空的
            if (vo.getId() != null){
                tbAddressDao.updateById(tbAddress);  //更新操作
            }else {
                tbAddressDao.insert(tbAddress);     //新增操作
            }
            result.put("result","success");
        } catch (BeansException e) {
            log.info("新增地址失败======失败原因："+e);
            result.put("result","后台异常，新增地址失败");
            return result;
        }

        return result;
    }

    /**
     * 删除地址
     * @param vo
     * @return
     */
    @Override
    public void deleteAddress(TbAdressDTO vo) {
        tbAddressDao.deleteById(vo.getId());
    }
}
