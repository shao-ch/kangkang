package com.kangkang.log.controller;

import com.kangkang.log.service.LogQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: LogQueryController  日志查询接口
 * @Author: shaochunhai
 * @Date: 2021/12/13 5:44 下午
 * @Description: TODO
 */
@Slf4j
@RestController
public class LogQueryController {

    @Autowired
    private LogQueryService logQueryService;

    @GetMapping(value = "/api/log/logQuery")
    public void logQuery(){

        log.info("=====日志查询接口，参数为；");

        logQueryService.logQuery();
    }
}
