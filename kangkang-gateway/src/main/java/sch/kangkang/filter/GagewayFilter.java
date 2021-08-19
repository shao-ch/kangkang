package sch.kangkang.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @ClassName: GagewayFilter
 * @Author: shaochunhai
 * @Date: 2021/8/16 5:57 下午
 * @Description: TODO
 */
@Component
public class GagewayFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        //获取用户的token
        String token = request.getHeaders().getFirst("token");

        //获取用户的code
        String code = request.getHeaders().getFirst("W_X_CODE");

        ServerHttpResponse response = exchange.getResponse();
        //判断是否携带了token并且code部位空
        if (token == null&&code==null) {

            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "500");
            jsonObject.put("data", "拒绝登陆");
            DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toString().getBytes(StandardCharsets.UTF_8));

            //没有token就返回拒绝登陆
            return response.writeWith(Mono.just(wrap));

            }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
