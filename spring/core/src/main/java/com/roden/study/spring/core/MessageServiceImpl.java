package com.roden.study.spring.core;

import org.springframework.stereotype.Component;

@Component
public class MessageServiceImpl implements MessageService {

    public String getMessage() {
        return "hello world";
    }
}