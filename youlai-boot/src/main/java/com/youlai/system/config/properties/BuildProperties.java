package com.youlai.system.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * 项目构建配置
 *
 * @author liuph
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "build")
public class BuildProperties {

    /**
     * 克隆地址
     */
    private String clonePath = System.getProperty("user.dir") + File.separator + "work" + File.separator + "git";

    /**
     * 打包地址
     */
    private String packagePath = System.getProperty("user.dir") + File.separator + "work" + File.separator + "package";

}
