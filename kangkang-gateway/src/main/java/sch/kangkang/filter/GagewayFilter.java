package sch.kangkang.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;

/**
 * @ClassName: GagewayFilter
 * @Author: shaochunhai
 * @Date: 2021/8/16 5:57 下午
 * @Description: TODO
 */
public class GagewayFilter implements GlobalFilter, Order {


    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    /**
     * 这里可以做鉴权，还有跨域处理
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return null;
    }

    /**
     * 设置过滤器优先级
     * @return
     */
    @Override
    public int value() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
