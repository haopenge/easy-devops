package com.easy.gateway.filter;

import com.easy.core.domain.Constants;
import com.easy.core.loadbalancer.EnvHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GrayLoadBalancerClientFilter extends ReactiveLoadBalancerClientFilter {
    public GrayLoadBalancerClientFilter(LoadBalancerClientFactory clientFactory, GatewayLoadBalancerProperties properties) {
        super(clientFactory, properties);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取入口环境，存入threadLocal
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String podEnv = headers.getFirst(Constants.POD_ENV);
        if(StringUtils.isNotEmpty(podEnv)){
            EnvHolder.setEnv(Constants.POD_ENV,podEnv);
        }

        return super.filter(exchange, chain);

    }
}
