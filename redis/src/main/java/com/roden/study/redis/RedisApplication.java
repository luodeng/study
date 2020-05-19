package com.roden.study.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RedisApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 继承SpringBootServletInitializer设置启动类,用于独立tomcat运行的入口
        return builder.sources(RedisApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }
}