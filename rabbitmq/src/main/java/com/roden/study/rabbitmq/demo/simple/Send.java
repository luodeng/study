package com.roden.study.rabbitmq.demo.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.roden.study.rabbitmq.demo.util.ConnectionUtil;



public class Send {
    private final static String QUEUE_NAME="q_test_01";

    public static void main(String[] args) throws Exception{
        //获取连接以及MQ通道
        Connection connection= ConnectionUtil.getConnection();
        //从连接中创建通道
        Channel channel=connection.createChannel();
        //声明（创建）队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //消息内容
        String message="Hello World!";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println(" [x] Sent '"+message+"'");
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
