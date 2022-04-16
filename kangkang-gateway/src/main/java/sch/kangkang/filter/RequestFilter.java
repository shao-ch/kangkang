package sch.kangkang.filter;

import com.alibaba.fastjson.JSONObject;
import com.kangkang.enumInfo.SkipWitchFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName: RequestFilter  过滤所有请求
 * @Author: shaochunhai
 * @Date: 2021/9/9 11:33 上午
 * @Description: TODO
 */
@Slf4j
//@Component
public class RequestFilter implements GlobalFilter, Ordered {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取系统标志
        String sysflag = exchange.getRequest().getHeaders().getFirst("SYSF");

        if (!"store".equals(sysflag)){
            //跳过次过滤器
            log.info("此系统为--["+sysflag+"]--跳过鉴权过滤器。");
            exchange.getAttributes().put(SkipWitchFilter.INIT_FILTER, "1");
            return chain.filter(exchange);
        }

        //这里要判断是否为登陆接口，如果是就直接跳过不需要去拿token了，key变了
        Object attribute = exchange.getAttribute(SkipWitchFilter.USER_TOKEN_FILTER);
        if (attribute==null||(boolean) attribute){
            //跳过次过滤器
            log.info("此路径--["+exchange.getRequest().getURI().getPath()+"]--跳过鉴权过滤器。");
            exchange.getAttributes().put(SkipWitchFilter.INIT_FILTER, "1");
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //获取用户的token
        String token = request.getHeaders().getFirst("token");

        if (token==null){
            response.getHeaders().set("Content-type", "text/html;charset=UTF-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "415");
            jsonObject.put("data", "请登陆后在操作");
            DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toString().getBytes());
            //没有token就返回拒绝登陆
            return response.writeWith(Mono.just(wrap));
        }

        //token是放在redis中的 需要去拿
        String actureToken = (String) redisTemplate.opsForValue().get(token);

        //将token刷新
        exchange.getRequest().mutate().header("token",actureToken).build();
        exchange.getAttributes().put(SkipWitchFilter.INIT_FILTER, "1");
        return chain.filter(exchange);
    }

    /**
     * 优先级最小
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 2;
    }
}
