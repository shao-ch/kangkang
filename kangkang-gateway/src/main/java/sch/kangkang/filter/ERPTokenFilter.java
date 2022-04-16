package sch.kangkang.filter;

import com.alibaba.fastjson.JSONObject;
import com.kangkang.enumInfo.SkipWitchFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName: ERPTokenFilter  erp系统的token拦截器，也是最后一个兜底过滤器
 * @Author: shaochunhai
 * @Date: 2022/4/16 10:10 上午
 * @Description: TODO
 */
@Slf4j
@Component
public class ERPTokenFilter implements GlobalFilter, Ordered  {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取系统标志
        String sysflag = exchange.getRequest().getHeaders().getFirst("SYSF");

        //这个是确定是否走了之前的过滤器
        Object isFilter = exchange.getAttributes().get(SkipWitchFilter.INIT_FILTER);
        if (!"erp".equals(sysflag)&&"0".equals(isFilter)){
            //跳过次过滤器
            log.info("此系统为--["+sysflag+"]--跳过鉴权过滤器。");
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().set("Content-type", "text/html;charset=UTF-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", "415");
            jsonObject.put("data", "此请求不属于该系统");
            DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toString().getBytes());
            //直接返回数据，因为 不属于该系统，这是最后一个过滤器
            return response.writeWith(Mono.just(wrap));
        }


        return chain.filter(exchange);
    }

    //最后一个过滤器
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 3;
    }
}
