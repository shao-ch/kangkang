package com.kangkang.file.service.impl;

import com.kangkang.file.dao.TbConfigInfoDao;
import com.kangkang.file.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.net.ftp.FTP;

/**
 * @ClassName: ConfigServiceImpl
 * @Author: shaochunhai
 * @Date: 2021/9/22 10:49 上午
 * @Description: TODO
 */
@Slf4j
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private TbConfigInfoDao tbConfigInfoDao;

    @Value("${file.hostname}")
    private String hostname;
    @Value("${file.username}")
    private String username;
    @Value("${file.password}")
    private String password;
    @Value("${file.commentPath}")
    private String commentPath;
    /**
     * 查询所有配置类信息
     * @return
     */
    @Override
    public HashMap<String, String> initConfigData() {

        HashMap<String,String> result=tbConfigInfoDao.queryAllConfig();
        return result;
    }


    /**
     * 文件上传
     * @param files
     * @return
     */
    @Override
    public String commentFileUpload(List<MultipartFile> files) {
        FTPClient client=null;
        try {
            //这里要去连接三次，如果三次还是不能连接成功就返回数据
            for (int i = 0; i < 3; i++) {
                //创建ftp实例
                 client = new FTPClient();
                //设置连接响应时间
                client.setConnectTimeout(10000);
                //连接
                client.connect(hostname,22);
                //登陆
                boolean login = client.login(username, password);
                if (!login){
                    log.info("评论上传文件，ftp登陆超时.......");
                    if (client.isConnected()){
                        //这里要关闭连接
                        client.disconnect();
                        //这里重新置为null
                        client=null;
                        continue;
                    }
                }
                //如果登陆成功就跳出循环
                // 设置字符编码
                client.setControlEncoding("UTF-8");
                //基本路径，一定存在
                String basePath="/";
                String[] pathArray = commentPath.split("/");
                for(String path:pathArray){
                    basePath+=path+"/";
                    //3.指定目录 返回布尔类型 true表示该目录存在
                    boolean dirExsists = client.changeWorkingDirectory(basePath);
                    //4.如果指定的目录不存在，则创建目录
                    if(!dirExsists){
                        //此方式，每次，只能创建一级目录
                        boolean flag=client.makeDirectory(basePath);
                        if (flag){
                           log.info("===="+basePath+"====目录创建成功！");
                        }
                    }
                }

                //重新指定上传文件的路径
                client.changeWorkingDirectory(basePath);
                //5.设置上传文件的方式
                client.setFileType(FTP.BINARY_FILE_TYPE);
                break;
            }

            if (client==null){
                return "ftp连接失败";
            }
            for (MultipartFile file : files) {

                //获取文件名
                String name = file.getName();
                InputStream inputStream = file.getInputStream();
                //重新指定上传
                boolean b = client.storeFile(name, inputStream);

                if (!b){
                    return "图片上传失败";
                }else {
                    log.info("【"+name+"】"+"===文件上传成功！");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
