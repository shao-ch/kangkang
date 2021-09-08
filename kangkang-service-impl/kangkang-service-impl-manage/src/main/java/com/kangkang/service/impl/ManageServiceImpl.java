package com.kangkang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kangkang.dao.KangKangUserDao;
import com.kangkang.dao.TbAddressDao;
import com.kangkang.dao.TbAreaDao;
import com.kangkang.manage.entity.TbUser;
import com.kangkang.manage.entity.TbAddress;
import com.kangkang.manage.entity.TbArea;
import com.kangkang.manage.viewObject.TbAdressVO;
import com.kangkang.service.ManageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ManageServiceImpl
 * @Author: shaochunhai
 * @Date: 2021/8/8 3:56 下午
 * @Description: TODO
 */
@Service("manageService")
public class ManageServiceImpl implements ManageService {


    @Autowired
    private KangKangUserDao kangKangUserDao;

    @Autowired
    private TbAddressDao tbAddressDao;

    @Autowired
    private TbAreaDao tbAreaDao;
    /**
     * 注册账号
     * @param tbUser
     */
    @Override
    @Transactional
    public void save(TbUser tbUser) {

        kangKangUserDao.insert(tbUser);
    }

    /**
     * 通过openid查询用户
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
     * 通过id查询用户地址
     * @param userId userId
     * @return
     */
    @Override
    public List<TbAdressVO> selectAddress(Integer userId) {

        ArrayList<TbAdressVO> result = new ArrayList<>();
        //查询所有收货地址
        List<TbAddress> address = tbAddressDao.selectAddress(userId);

        if (address.isEmpty()){
            return result;
        }
        for (TbAddress tbAddress : address) {
            TbAdressVO vo = new TbAdressVO();
            //数据转换
            BeanUtils.copyProperties(address,vo);
            //及联查询所有具体地址
            List<TbArea> tbArea = tbAreaDao.selectAllLevel(tbAddress.getAreaid());
            //查询的省市县
            vo.setAreas(tbArea);
            result.add(vo);

        }
        return result;
    }


    /**
     * 新增收货地址
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void commitAddress(TbAdressVO vo) {

       if (StringUtils.equals(vo.getPriority(),"0")){
           //将所有地址更新为1
           tbAddressDao.updatePriorityByUserId(vo.getUserId(),'1');
       }

        TbAddress tbAddress = new TbAddress();
       //数据转换
        BeanUtils.copyProperties(vo,tbAddress);

        tbAddressDao.insert(tbAddress);
    }
}
