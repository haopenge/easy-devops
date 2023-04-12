package com.easy.api.config;

import com.easy.api.exception.ServiceException;
import io.kubernetes.client.ProtoClient;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.ExtensionsV1beta1Api;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.credentials.Authentication;
import io.kubernetes.client.util.credentials.KubeconfigAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static com.easy.api.domain.enumx.FailureEnum.K8S_DEPLOY_DEPLOYMENT;

@Slf4j
@Configuration
public class K8sClientConfiguration {

    @Value("${k8s.base_path:https://10.8.0.1:6443}")
    private String basePath;

    @Bean
    public ApiClient getApiClient(Authentication kubeConfigAuthentication) {
        return new ClientBuilder()
                .setBasePath(basePath)
                .setAuthentication(kubeConfigAuthentication)
                .setVerifyingSsl(false)
                .build();
    }

    @Bean
    public Authentication kubeConfigAuthentication(){
        try (        InputStream is = this.getClass().getClassLoader().getResourceAsStream("kube.yaml")
        ) {
            assert is != null;
            try (Reader fr = new InputStreamReader(is)
            ){
                KubeConfig config = KubeConfig.loadKubeConfig(fr);
                return new KubeconfigAuthentication(config);
            }
        } catch (Exception e) {
            log.error("kubeConfigAuthentication error: " , e);
            throw new ServiceException(K8S_DEPLOY_DEPLOYMENT);
        }
    }

    @Bean
    public AppsV1Api getAppsV1Api(ApiClient apiClient) {
        return new AppsV1Api(apiClient);
    }

    @Bean
    public ProtoClient getProtoClient(ApiClient apiClient) {
        return new ProtoClient(apiClient);
    }

    @Bean
    public CoreV1Api coreV1Api(ApiClient apiClient) {
        return new CoreV1Api(apiClient);
    }

    @Bean
    public ExtensionsV1beta1Api extensionsV1beta1Api(ApiClient apiClient) {
        return new ExtensionsV1beta1Api(apiClient);
    }

}
