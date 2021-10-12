package com.kangkang.file.dao;

import com.kangkang.file.utils.BatchBaseMapper;
import com.kangkang.manage.entity.TbSign;
import org.springframework.stereotype.Repository;

/**
 * @InterfaceName: TbSignDao  签到表
 * @Author: shaochunhai
 * @Date: 2021/10/12 2:54 下午
 * @Description: TODO
 */

@Repository
public interface TbSignDao extends BatchBaseMapper<TbSign> {
}
