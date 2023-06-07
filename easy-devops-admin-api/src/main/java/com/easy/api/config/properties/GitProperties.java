package com.easy.api.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * git配置
 *
 * @author liuph
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "git")
public class GitProperties {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 仓库项目获取api-url
     */
    private String projectFindUrl;

    /**
     * 仓库详情项目获取api-url
     */
    private String projectDetailFindUrl;
}
