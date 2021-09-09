package sch.kangkang.filter;

import com.alibaba.fastjson.JSONObject;
import com.kangkang.enumInfo.SkipWitchFilter;
import com.kangkang.tools.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import sch.kangkang.wxLogin.WxUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: UserTokenFilter   判断用户登陆的时候是否携带token
 * @Author: shaochunhai
 * @Date: 2021/8/17 3:20 下午
 * @Description: TODO
 */
@Component
public class UserTokenFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(UserTokenFilter.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("=======进入token检验过滤器=====");
        boolean attribute = exchange.getAttribute(SkipWitchFilter.USER_TOKEN_FILTER);
        if (!attribute){
            //跳过次过滤器
            log.info("此路径--["+exchange.getRequest().getURI().getPath()+"]--跳过鉴权过滤器。");
            return chain.filter(exchange);
        }
        //获取response
        ServerHttpResponse response = exchange.getResponse();
        try {
            ServerHttpRequest request = exchange.getRequest();
            //获取用户的token
            String token = request.getHeaders().getFirst("token");
            log.info("=======获取用户token：【"+token+"】=====");
            //获取用户的code
            String code = request.getHeaders().getFirst("W_X_CODE");

            if (token == null) {
                //如果code不为空就去请求wx接口
                if (code != null) {
                    String wxUrl = getUrl(code);
                    log.info("=======获取微信请求地址：【"+wxUrl+"】=====");
                    //获取wx返回结果
                    String wxresult = sendWx(wxUrl);

                    Map<String, Object> map = JSONObject.parseObject(wxresult, Map.class);

                    if (map == null || map.size() == 0) {
                        //直接返回数据
                        return getReturnData(response, "调用微信接口失败", "415");
                    }
                    if (map.get("errcode")!=null) {
                        return getReturnData(response, map.get("errmsg").toString(), "415");
                    }
                    //成功之后这里要对信息进行jwt加密处理，存入redis，然后返回给前端
                    String getToken = JwtUtils.generateJsonWebToken(map);
                    log.info("=======用户的token为：【"+wxUrl+"】=====");
                    Object openid = map.get("openid");
                    //对token加密处理
                    String tokens= Base64Utils.encodeToString(openid.toString().getBytes(StandardCharsets.UTF_8));
                    //判断token是否存在，如果已经存在直接返回
                    if (redisTemplate.opsForValue().get(tokens) != null) {
                        log.info("=======用户的token已存在，为：【"+redisTemplate.opsForValue().get(tokens)+"】=====");
                        return  getMono(response, "200", openid, tokens);
                    }
                    //将token存入redis
                    redisTemplate.opsForValue().set(tokens, getToken, WxUtil.redis_Timeout, TimeUnit.HOURS);
                    log.info("=======token存入redis成功=====");
                    //将token返回前端
                    return getMono(response, "200", openid, tokens);
                }
            } else {   //这里是携带token的,并且token的内容为openid

                //token是放在redis中的 需要去拿
                String actureToken = (String) redisTemplate.opsForValue().get(token);

                //判断缓存中是不是不存在，或者本身token自己过期了
                if (actureToken == null||checkToken(actureToken)) {
                    return getReturnData(response, "token以失效,请重新登陆", "415");
                }
                //设置请求头权限，不然会报java.lang.UnsupportedOperationException，因为所有的请求头是没有修改的权限

                exchange.getRequest().mutate().header("token",actureToken).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("===网关服务异常===："+e);

            return getReturnData(response, "网关服务异常", "500");
        }
        //继续往后面的服务传
        return chain.filter(exchange);
    }

    /**
     * 将认证成功的数据返回给前端
     * @param response
     * @param code
     * @param openid
     * @param tokens
     * @return
     */
    private Mono<Void> getMono(ServerHttpResponse response, String code, Object openid, String tokens) {
        response.setStatusCode(HttpStatus.valueOf(Integer.valueOf(code)));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("token", tokens);
        data.put("openid", openid);
        jsonObject.put("data",data);
        DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
        //没有token就返回拒绝登陆
        return response.writeWith(Mono.just(wrap));
    }


    /**
     * 检查token本身是否失效
     * @param token   true  代表token已经过期
     * @return
     */
    private boolean checkToken(String token) {

        boolean b = JwtUtils.checkExpiration(token);

        return b;
    }

    /**
     * 拒绝请求之后 --->响应数据信息
     * @param response
     * @param errorMsg
     * @param code
     * @return
     */
    private Mono<Void> getReturnData(ServerHttpResponse response, String errorMsg, String code) {
        //设置前端编码器，不然会报中文乱码
        response.getHeaders().set("Content-type", "text/html;charset=UTF-8");
        response.setStatusCode(HttpStatus.valueOf(Integer.valueOf(code)));
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
    private String  sendWx(String wxUrl) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(wxUrl);
            //打开连接
            connection = (HttpURLConnection) url.openConnection();
            //连接回话
            connection.connect();
            log.info("=======微信连接成功=====");
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
        String url = "";
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
