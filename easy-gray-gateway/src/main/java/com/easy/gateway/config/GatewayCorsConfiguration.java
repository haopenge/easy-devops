package com.easy.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "gateway.cors")
public class GatewayCorsConfiguration {

    private List<String> allowHeadList = Collections.singletonList("*");

    private List<String> allowMethodList = Collections.singletonList("*");

    private boolean allowCredentialEnable = true;

    private String allowedOriginPatterns = "*";

    private Long maxAge = 18000L;


    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration config = new CorsConfiguration();
        // 允许cookies跨域
        config.setAllowCredentials(allowCredentialEnable);
        config.addAllowedOriginPattern(allowedOriginPatterns);

        // 设置允许访问的header
        if(CollectionUtils.isEmpty(allowHeadList)){
            config.addAllowedHeader("*");
        }
        allowHeadList.forEach(config::addAllowedHeader);
        
        // 设置允许访问的method
        if(CollectionUtils.isEmpty(allowMethodList)){
            config.addAllowedMethod("*");
        }
        allowMethodList.forEach(config::addAllowedMethod);

        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(maxAge);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

}
