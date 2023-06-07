package com.easy.devops.api.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 管理页面配置
 *
 * @author liuph
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "admin")
public class AdminApiProperties {

    /**
     * 是否打印sql操作日志
     */
    private boolean sqlLogEnabled = false;
}
