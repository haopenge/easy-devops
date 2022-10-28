package com.easy.controller;

import com.easy.intf.EatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eat")
public class EatController {

    @Autowired
    private EatService eatService;

    /**
     * 吃
     */
    @GetMapping("/apple")
    public String eatApple() {
        return "provider: " + eatService.eatApple();
    }

    @GetMapping("/orange")
    public String eatOrange(String who) {
        return eatService.eatOrange(who);
    }
}
