package com.kangkang.file.service;

import com.kangkang.service.KangkangFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
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
    List<Map<String, String>> initConfigData();

    /**
     * 文件上传
     * @param files
     * @return
     */
    String commentFileUpload(List<MultipartFile> files) throws IOException;

    void queryTest();

}
