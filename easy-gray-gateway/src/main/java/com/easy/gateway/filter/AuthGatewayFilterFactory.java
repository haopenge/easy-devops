package com.easy.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.easy.core.domain.ApiResult;
import com.easy.core.enumx.FailureEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 网关过滤器
 *
 * @author liupenghao
 */
@Slf4j
@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {

    /**
     * 用户登录状态token 字段
     */
    @Value("${user_token_field:token}")
    private String userTokenField;

    public AuthGatewayFilterFactory() {
        super(NameConfig.class);
        log.info("AuthGatewayFilterFactory init success");
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(NAME_KEY);
    }

    @Override
    public GatewayFilter apply(NameConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            // 校验是否是 不用登录的URL
            String path = request.getPath().toString();
            log.info("AuthGatewayFilterFactory.apply path:{}", path);

            String ignoreUrlListStr = config.getName();
            log.info("AuthGatewayFilterFactory.apply ignoreUrlListStr={}", ignoreUrlListStr);

            boolean ignoreOk = Arrays.asList(ignoreUrlListStr.split("\\|")).contains(path);
            if (ignoreOk) {
                return chain.filter(exchange);
            }

            // 校验是否登录
            HttpHeaders headers = request.getHeaders();
            String userToken = headers.getFirst(userTokenField);
            if (StringUtils.isEmpty(userToken)) {
                // 返回未登录提示
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);

                byte[] responseByteArray = JSON.toJSONBytes(ApiResult.error(FailureEnum.AUTH_TOKEN_EXPIRED.getCode(), FailureEnum.AUTH_TOKEN_EXPIRED.getMessage()));
                DataBuffer responseBuffer = response.bufferFactory().allocateBuffer(responseByteArray.length).write(responseByteArray);
                return response.writeWith(Mono.just(responseBuffer));
            }
            log.info("AuthGatewayFilterFactory.apply  token = {}", userToken);
            return chain.filter(exchange);
        };
    }
}
