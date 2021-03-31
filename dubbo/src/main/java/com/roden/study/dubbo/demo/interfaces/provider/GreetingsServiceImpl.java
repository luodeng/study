package com.roden.study.dubbo.demo.interfaces.provider;

import com.roden.study.dubbo.demo.api.GreetingsService;

public class GreetingsServiceImpl implements GreetingsService {
    @Override
    public String sayHi(String name) {
        return "hi, " + name;
    }
}