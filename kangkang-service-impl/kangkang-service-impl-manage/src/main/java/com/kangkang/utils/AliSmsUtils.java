package com.kangkang.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import javax.xml.soap.SAAJResult;
import java.util.Random;

/**
 * @ClassName: AliSmsUtils  阿里云发送短信
 * @Author: shaochunhai
 * @Date: 2022/3/2 4:53 下午
 * @Description: TODO
 */
public class AliSmsUtils {
    static final String product = "Dysmsapi";
    // 产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
    //短信签名
    private static String signName = "优品惠";
    static final String accessKeyId = "LTAI5tJpXcz8yYDRrVbd7dYx";           // TODO 改这里
    static final String accessKeySecret = "BKjKqhi7L63IWNIF1iw2vcP4E9WsCK"; // TODO 改这里


    /**
     * 发送短信验证
     * @param code   模版中的变量
     * @param telephone   电话号码
     * @throws ClientException
     */
    public static SendSmsResponse sendSms(String code, String telephone) throws ClientException {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //这里就是你要发送的验证码之类的东西
        SendSmsRequest send = new SendSmsRequest();

        send.setPhoneNumbers(telephone);

        send.setSignName(signName);
        // 必填:短信模板-可在短信控制台中找到  SMS_235815415
        send.setTemplateCode("SMS_171855556");
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的用户,您的验证码为${code}"时,此处的值为
        send.setTemplateParam("{\"code\":\"" + code + "\"}");

        // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");
        // hint 此处可能会抛出异常，注意catch
        return acsClient.getAcsResponse(send);


    }


    /**
     * 获取6位数随机数
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

}
