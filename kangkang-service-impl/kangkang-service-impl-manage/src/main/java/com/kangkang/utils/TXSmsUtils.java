package com.kangkang.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.kangkang.config.ConfigDataContext;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

import java.util.Random;

/**
 * @ClassName: TXSmsUtils  腾讯云发送短信
 * @Author: shaochunhai
 * @Date: 2022/3/2 4:53 下午
 * @Description: TODO
 */

@Component
public class TXSmsUtils {
    private  static String SDKAppID;
    private  static String secretId;
    private  static String secretKey;
    //短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，可登录 [短信控制台] 查看签名信息
    private  static String SIGN;
    private  static String TEMPLATEID;

    //初始化代码块信息
    static {
        SDKAppID= ConfigDataContext.getValue("SDKAppID");
        secretId= ConfigDataContext.getValue("secretId");
        secretKey= ConfigDataContext.getValue("secretKey");
        SIGN= ConfigDataContext.getValue("SIGN");
        TEMPLATEID= ConfigDataContext.getValue("TEMPLATEID");
    }

    //或者@PostConstruct也行

    /**
     *
     * @param phoneNumbers  电话号码  这个是公共的类，所以这里可以通过电话号码批量发送，电话号码是个数组
     * @param templateParamSet  你要发送的内容
     * @param type  这个代表你要发送的短信模板类型，0-发送验证码
     * @return
     * @throws TencentCloudSDKException
     */
    public  SendStatus sendSMS(String[] phoneNumbers,String[] templateParamSet,String type) throws TencentCloudSDKException {

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
        SmsClient client = new SmsClient(credential, "ap-shanghai",clientProfile);
        SendSmsRequest req = new SendSmsRequest();

        //短信应用ID
        req.setSmsSdkAppid(SDKAppID);
        //短信签名内容
        req.setSign(SIGN);
        /* 模板 ID: 必须填写已审核通过的模板 ID。模板ID可登录 [短信控制台] 查看 */
        //根据不同业务使用不同的模板
        switch (type){
            case "1":
                req.setTemplateID(TEMPLATEID);
                break;
//            case "2":
//                req.setTemplateID(TEMPLATEID);
//                break;
        }
        //这里要加上国际区号  国内是要加上+86的
        for (int i = 0; i < phoneNumbers.length; i++) {
            if (!phoneNumbers[i].contains("+86")){
                phoneNumbers[i]="+86"+phoneNumbers[i];
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

    public static void main(String[] args) throws  TencentCloudSDKException {
        // 通过应用秘钥创建 发送一条信息的对象
        String rodomNum = getRodomNum();
        String[] phones = {"+8619109624826"};
        String[] context={rodomNum,"5"};

        SendStatus sendStatus = new TXSmsUtils().sendSMS(phones, context, "1");

        System.out.println("sendSmsResponse = " + sendStatus.getCode());
        System.out.println("sendSmsResponse = " + sendStatus.getMessage());
        System.out.println("sendSmsResponse = " + sendStatus.getPhoneNumber());
        System.out.println("sendSmsResponse = " + sendStatus.getSessionContext());
        System.out.println("sendSmsResponse = " + sendStatus.getFee());


    }
}
