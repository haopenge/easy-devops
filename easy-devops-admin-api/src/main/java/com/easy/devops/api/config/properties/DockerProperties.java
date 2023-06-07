package com.easy.devops.api.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * docker配置
 *
 * @author liuph
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "docker")
public class DockerProperties {

    /**
     * 仓库地址
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
