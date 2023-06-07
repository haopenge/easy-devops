package com.easy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.easy.intf"})
@SpringBootApplication
public class EasyGrayConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyGrayConsumerApplication.class, args);
    }

}
