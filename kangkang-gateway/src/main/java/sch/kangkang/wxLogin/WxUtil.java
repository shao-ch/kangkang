package sch.kangkang.wxLogin;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName: WxUtil
 * @Author: shaochunhai
 * @Date: 2021/8/17 4:00 下午
 * @Description: TODO
 */
@Component
public class WxUtil implements InitializingBean {

    @Value("${wx.open.app_id}")
    private String appId;

    @Value("${wx.open.app_secret}")
    private String appSecret;

    @Value("${wx.open.redirect_url}")
    private String redirectUrl;
    @Value("${wx.sch.timeout}")
    private Integer timeout;

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;
    public static Integer redis_Timeout;

    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID=appId;
        WX_OPEN_APP_SECRET=appSecret;
        WX_OPEN_REDIRECT_URL=redirectUrl;
        redis_Timeout=timeout;
    }
}
