package com.roden.study.rabbitmq.demo.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


import com.rabbitmq.client.DeliverCallback;
import com.roden.study.rabbitmq.demo.util.ConnectionUtil;

public class Recv {
    private final static String QUEUE_NAME="q_test_01";

    public static void main(String[] args) throws Exception {
        //获取连接以及mq通道
        Connection connection= ConnectionUtil.getConnection();
        //从连接中创建通道
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        DeliverCallback dc = (consumerTag,delivery)->{
            String message=new String(delivery.getBody(),"UTF-8");
            System.out.println(" [x] Received '"+message+"'");
        };


        channel.basicConsume(QUEUE_NAME,true,dc,consumerTag->System.out.println("CancelCallback consumerTag:"+consumerTag));



    }
}
