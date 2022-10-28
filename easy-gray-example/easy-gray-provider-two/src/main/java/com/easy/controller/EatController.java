package com.easy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@RestController
@RequestMapping("/eat")
public class EatController {

    @Value("${server.port}")
    private Integer port;

    /**
     * 吃
     */
    @GetMapping("/apple")
    public String eatApple(HttpServletRequest request) {
        System.out.println("-------》哎呀呀  我被调用了  com.husky.controller.EatController.eatApple");
        return " 我吃了 苹果 on " + port;
    }

    @GetMapping("/orange")
    public String eatOrange(String who) {
        System.out.println("-------》哎呀呀  我被调用了  com.husky.controller.EatController.eatOrange");
        return who + " eat orange";
    }
}
