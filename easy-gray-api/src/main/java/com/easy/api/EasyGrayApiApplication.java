package com.easy.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.easy.api.mapper")
@SpringBootApplication
public class EasyGrayApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyGrayApiApplication.class, args);
    }

}
