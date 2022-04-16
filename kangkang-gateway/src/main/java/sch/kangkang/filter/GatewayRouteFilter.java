package sch.kangkang.filter;

import com.alibaba.fastjson.JSONObject;
import com.kangkang.enumInfo.SkipWitchFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: GatewayRouteFilter  路由过滤器  可以指定跳过哪个路由器
 * @Author: shaochunhai
 * @Date: 2021/8/16 5:57 下午
 * @Description: TODO
 */
//@Component
public class GatewayRouteFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(GatewayRouteFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("=====请求以进网关，；路径为===[" + exchange.getRequest().getURI().getPath() + "]=========");

        ServerHttpRequest request = exchange.getRequest();
        //获取请求路径
        String path = request.getURI().getPath();
        //指定跳过哪个拦截器
        if (path.endsWith("mini/manage/auth") || path.endsWith("/manage/api/signIn")) {
            exchange.getAttributes().put(SkipWitchFilter.USER_TOKEN_FILTER, true);
        } else if (path.endsWith("/mini/manage/erp/ERPLogin")) {
            exchange.getAttributes().put(SkipWitchFilter.ERP_TOKEN_FILTER, true);
        } else {
            exchange.getAttributes().put(SkipWitchFilter.USER_TOKEN_FILTER, false);
        }
        exchange.getAttributes().put(SkipWitchFilter.INIT_FILTER, "0");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
