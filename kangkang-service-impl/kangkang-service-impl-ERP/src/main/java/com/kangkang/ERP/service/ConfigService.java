package com.kangkang.ERP.service;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName: ConfigService  配置类服务
 * @Author: shaochunhai
 * @Date: 2021/9/22 10:27 上午
 * @Description: TODO
 */
public interface ConfigService {

    /**
     * 查询所有配置类信息
     * @return
     */
    List<Map<String,String>> initConfigData();

}
