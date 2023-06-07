package com.easy.devops.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @author liupenghao
 */
@MapperScan(basePackages = "com.easy.api.mapper")
@Configuration("com.easy.api")
@SpringBootApplication
public class EasyGrayApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyGrayApiApplication.class, args);
    }

}
