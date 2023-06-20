package com.youlai.system.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 灰度配置
 *
 * @author liuph
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "k8s")
public class K8sProperties {

    /**
     * k8s基础路径地址
     */
    private String basePath;

    /**
     * k8s路径地址
     */
    private String path = "/api/v1/namespaces/";
}
