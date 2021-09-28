package com.kangkang.file.controller;

import com.kangkang.file.service.ConfigService;
import com.kangkang.tools.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName: FileProcessController
 * @Author: shaochunhai
 * @Date: 2021/9/22 10:15 上午
 * @Description: TODO
 */
@Slf4j
@RestController
@RequestMapping()
public class FileProcessController {

    @Autowired
    private ConfigService configService;

    /**
     * 评论文件上传
     *
     * @param files
     * @return
     */
    @PostMapping("/commentFileUpload")
    public ResponseCode<String> commentFileUpload(@RequestParam(value = "files") List<MultipartFile> files) {

        if (files.isEmpty()) {
            return ResponseCode.message(500, null, "文件不能为空");
        }
        //这里不需要判断图片大小。前台会判断
        try {
            String result = configService.commentFileUpload(files);
            if (result.equals("上传成功"))
                return ResponseCode.message(200, result, "success");
            //上传失败
            return ResponseCode.message(500, result, "fail");
        } catch (Exception e) {
            log.error("上传文件异常：" + e);

            return ResponseCode.status(500).body("服务异常");
        }
    }
}
