package com.roden.study.redis.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Value("${testValue}")
    private String testValue;

    @GetMapping("/test")
    public Object test(){
        System.out.println(testValue);
        return testValue;
    }
}
