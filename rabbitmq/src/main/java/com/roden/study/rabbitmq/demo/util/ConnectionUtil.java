package com.roden.study.rabbitmq.demo.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
    public static Connection getConnection()throws Exception{
        //定义连接工厂
        ConnectionFactory cf=new ConnectionFactory();
        //设置服务地址
        cf.setHost("localhost");
        //端口
        cf.setPort(5672);
        //设置账号信息 用户名 密码 vhost
        cf.setVirtualHost("testhost");
        cf.setUsername("admin");
        cf.setPassword("luodeng");
        //通过工厂获取连接
        return cf.newConnection();
    }
}
