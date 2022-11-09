package com.easy.api.config;

import io.kubernetes.client.ProtoClient;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class K8sClientConfiguration {

    @Value("${k8s.base_path:}")
    private String basePath;

    @Value("${k8s.token:}")
    private String token;

    @Bean
    public ApiClient getApiClient() {
        AccessTokenAuthentication authentication = new AccessTokenAuthentication(token);
        return new ClientBuilder()
                .setBasePath(basePath)
                .setAuthentication(authentication)
                .setVerifyingSsl(false)
                .build();
    }

    @Bean
    public AppsV1Api getAppsV1Api(ApiClient apiClient) {
        return new AppsV1Api(apiClient);
    }

    @Bean
    public ProtoClient getProtoClient(ApiClient apiClient) {
        return new ProtoClient(apiClient);
    }


}
