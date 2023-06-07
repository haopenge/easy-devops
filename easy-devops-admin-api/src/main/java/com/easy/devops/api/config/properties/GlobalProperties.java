package com.easy.devops.api.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * 全局配置
 *
 * @author liuph
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "global")
public class GlobalProperties {

    /**
     * 是否打印sql操作日志
     */
    private boolean sqlLogEnabled = false;


    /**
     * ssh-私钥凭证保存路径
     */
    private String authSshPrivateKeyPath = System.getProperty("user.dir") + File.separator + "work" + File.separator + "auth";

}
