package sch.kangkang.filter;

import com.alibaba.fastjson.JSONObject;
import com.kangkang.tools.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import sch.kangkang.wxLogin.WxUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: UserTokenFilter   判断用户登陆的时候是否携带token
 * @Author: shaochunhai
 * @Date: 2021/8/17 3:20 下午
 * @Description: TODO
 */
@Component
public class UserTokenFilter implements GlobalFilter, Ordered {

    //redis的过期时间，单位为小时
    @Value("redis.timeout")
    private Integer timeout;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取response
        ServerHttpResponse response = exchange.getResponse();
        try {
            ServerHttpRequest request = exchange.getRequest();
            //获取用户的token
            String token = request.getHeaders().getFirst("token");

            //获取用户的code
            String code = request.getHeaders().getFirst("W_X_CODE");

            if (token == null) {
                //如果code不为空就去请求wx接口
                if (code != null) {
                    String wxUrl = getUrl(code);
                    //获取wx返回结果
                    String wxresult = sendWx(wxUrl);

                    Map<String, Object> map = JSONObject.parseObject(wxresult, Map.class);

                    if (map == null || map.size() == 0) {
                        //直接返回数据
                        return getReturnData(response, "拒绝登陆", "500");
                    }
                    if (!map.get("errcode").equals("0")) {
                        return getReturnData(response, map.get("errmsg").toString(), "500");
                    }
                    //成功之后这里要对信息进行jwt加密处理，存入redis，然后返回给前端
                    String getToken = JwtUtils.generateJsonWebToken(map);

                    Object openid = map.get("openid");
                    //将token存入redis
                    redisTemplate.opsForValue().set(openid, getToken, timeout, TimeUnit.HOURS);
                    //将token返回前端
                    return getReturnData(response, String.valueOf(openid), "200");
                }
            } else {   //这里是携带token的,并且token的内容为openid

                //token是放在redis中的 需要去拿
                String o = (String) redisTemplate.opsForValue().get(token);

                if (o == null) {
                    return getReturnData(response, "token以失效", "401");
                }
                //将真实token存入请求头中
                request.getHeaders().set("token", o);
            }
        } catch (Exception e) {
            e.printStackTrace();

            return getReturnData(response, "网关服务异常", "500");
        }
        //继续往后面的服务传
        return chain.filter(exchange);
    }

    private Mono<Void> getReturnData(ServerHttpResponse response, String errorMsg, String code) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("data", errorMsg);
        DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
        //没有token就返回拒绝登陆
        return response.writeWith(Mono.just(wrap));
    }

    /**
     * 发送微信请求
     *
     * @param wxUrl
     */
    private String sendWx(String wxUrl) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(wxUrl);
            //打开连接
            connection = (HttpURLConnection) url.openConnection();
            //连接回话
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

            String line = "";
            //获取返回数据
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        return builder.toString();
    }


    /**
     * 获取微信url
     *
     * @param code
     * @return
     */
    private String getUrl(String code) {
        String url = null;
        if (WxUtil.WX_OPEN_REDIRECT_URL != null && WxUtil.WX_OPEN_APP_ID != null && WxUtil.WX_OPEN_APP_SECRET != null) {
            url += WxUtil.WX_OPEN_REDIRECT_URL + "?appid=" + WxUtil.WX_OPEN_APP_ID + "&secret="
                    + WxUtil.WX_OPEN_APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        }

        return url;
    }


    //优先级低于跨域
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
