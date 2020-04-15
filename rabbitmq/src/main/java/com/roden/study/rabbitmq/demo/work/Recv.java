package com.roden.study.rabbitmq.demo.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.roden.study.rabbitmq.demo.util.ConnectionUtil;

public class Recv {
    private final static String QUEUE_NAME="test_queue_work";

    public static void main(String[] args) throws Exception{
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        DeliverCallback dc = (consumerTag, delivery)->{
            String message=new String(delivery.getBody(),"UTF-8");
            System.out.println(" [x] Received '"+message+"'");
            try {
                // 休眠1秒
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //下面这行注释掉表示使用自动确认模式
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        // 监听队列，false表示手动返回完成状态，true表示自动
        channel.basicConsume(QUEUE_NAME,false,dc,consumerTag->System.out.println("CancelCallback consumerTag:"+consumerTag));


    }
}
