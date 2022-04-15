package com.kangkang.config;

import com.kangkang.service.ConfigService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName: TXSmsUtils  腾讯云发送短信
 * @Author: shaochunhai
 * @Date: 2022/3/2 4:53 下午
 * @Description: TODO
 */
@Slf4j
@Configuration
public class TXSmsUtils implements InitializingBean, ServletContextAware {

    @Autowired
    private ConfigService configService;

    private String SDKAppID;
    private String secretId;
    private String secretKey;
    //短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，可登录 [短信控制台] 查看签名信息
    private String SIGN;
    private String TEMPLATEID;

    private static HashMap<String, String> result;

    //初始化代码块信息


    /**
     * 腾讯云信息属性注入
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        SDKAppID = result.get("SDKAppID");
        secretId = result.get("secretId");
        secretKey = result.get("secretKey");
        SIGN = result.get("SIGN");
        TEMPLATEID = result.get("TEMPLATEID");
    }

    /**
     * 腾讯云配置文件加载
     * @param servletContext
     */
    @Override
    public void setServletContext(ServletContext servletContext) {

        log.info("=========开始获取配置信息============");
        //容器启动的时候去获取配置信息
        try {
            List<Map<String, String>> list = configService.initConfigData();
            result = new HashMap<>();
            for (Map<String, String> map : list) {
                result.put(map.get("confKey"), map.get("confValue"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取配置信息失败：" + e);
        }

    }

    //或者@PostConstruct也行

    /**
     * @param phoneNumbers     电话号码  这个是公共的类，所以这里可以通过电话号码批量发送，电话号码是个数组
     * @param templateParamSet 你要发送的内容
     * @param type             这个代表你要发送的短信模板类型，0-发送验证码
     * @return
     * @throws TencentCloudSDKException
     */
    public SendStatus sendSMS(String[] phoneNumbers, String[] templateParamSet, String type) throws TencentCloudSDKException {

        //获取腾讯云认证
        Credential credential = new Credential(secretId, secretKey);

        //实例化一个http实例，api就是这样要求的。我也不知道什么意思
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(60);
        //实例化一个客户端配置对象，指定超时时间等配置
        ClientProfile clientProfile = new ClientProfile();
        /* SDK默认用TC3-HMAC-SHA256进行签名
         * 非必要请不要修改这个字段 */
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);
        SmsClient client = new SmsClient(credential, "ap-shanghai", clientProfile);
        SendSmsRequest req = new SendSmsRequest();

        //短信应用ID
        req.setSmsSdkAppid(SDKAppID);
        //短信签名内容
        req.setSign(SIGN);
        /* 模板 ID: 必须填写已审核通过的模板 ID。模板ID可登录 [短信控制台] 查看 */
        //根据不同业务使用不同的模板
        switch (type) {
            case "1":
                req.setTemplateID(TEMPLATEID);
                break;
//            case "2":
//                req.setTemplateID(TEMPLATEID);
//                break;
        }
        //这里要加上国际区号  国内是要加上+86的
        for (int i = 0; i < phoneNumbers.length; i++) {
            if (!phoneNumbers[i].contains("+86")) {
                phoneNumbers[i] = "+86" + phoneNumbers[i];
            }
        }
        req.setPhoneNumberSet(phoneNumbers);
        /* 模板参数: 若无模板参数，则设置为空 */
        req.setTemplateParamSet(templateParamSet);
        SendSmsResponse res = client.SendSms(req);
        return res.getSendStatusSet()[0];
    }


    /**
     * 获取6位数随机数
     *
     * @return
     */
    public static String getRodomNum() {

        String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
        Random rand = new Random();
        StringBuffer flag = new StringBuffer();
        for (int j = 0; j < 6; j++) {
            flag.append(sources.charAt(rand.nextInt(9)) + "");
        }

        return flag.toString();
    }


//    public static void main(String[] args) throws  TencentCloudSDKException {
//        // 通过应用秘钥创建 发送一条信息的对象
//        String rodomNum = getRodomNum();
//        String[] phones = {"+8619109624826"};
//        String[] context={rodomNum,"5"};
//
//        SendStatus sendStatus = new TXSmsUtils().sendSMS(phones, context, "1");
//
//        System.out.println("sendSmsResponse = " + sendStatus.getCode());
//        System.out.println("sendSmsResponse = " + sendStatus.getMessage());
//        System.out.println("sendSmsResponse = " + sendStatus.getPhoneNumber());
//        System.out.println("sendSmsResponse = " + sendStatus.getSessionContext());
//        System.out.println("sendSmsResponse = " + sendStatus.getFee());
//
//
//    }
}
