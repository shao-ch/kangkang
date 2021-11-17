package sch.kangkang.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @ClassName: AddHostKeyResolver  添加ip限流器  (仅仅限制指定ip的流量)
 * @Author: shaochunhai
 * @Date: 2021/11/16 10:34 上午
 * @Description: TODO
 */
@Slf4j
@Component("addHostKeyResolver")
public class AddHostKeyResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        URI uri = exchange.getRequest().getURI();
        log.info("=======请求的uri为："+uri.getPath());
        return Mono.just(exchange.getRequest().getPath().value());
    }
}
