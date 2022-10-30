package com.yiyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.easy.gateway")
public class EasyGrayGatewayApplication {

    public static void main(String[] args){
        SpringApplication.run(EasyGrayGatewayApplication.class, args);
    }
}
