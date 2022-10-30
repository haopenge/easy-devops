package com.easy.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class HeaderLimitGatewayFilterFactory extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {
    public HeaderLimitGatewayFilterFactory() {
        super(NameConfig.class);
        log.info("HeaderLimitGatewayFilterFactory init success");
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(NAME_KEY);
    }

    @Override
    public GatewayFilter apply(NameConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String allowHeaderStr = config.getName();
            if(StringUtils.isBlank(allowHeaderStr)){
                return chain.filter(exchange);
            }

            List<String> allowHeaderList = Arrays.stream(allowHeaderStr.split("\\|")).collect(Collectors.toList());
            List<String> needRemoveHeaders = new ArrayList<>();

            HttpHeaders headers = request.getHeaders();
            headers.forEach((key,value) -> {
                if(!allowHeaderList.contains(key)){
                    needRemoveHeaders.add(key);
                }
            });
            needRemoveHeaders.forEach(headers::remove);
            return chain.filter(exchange);
        };
    }
}
