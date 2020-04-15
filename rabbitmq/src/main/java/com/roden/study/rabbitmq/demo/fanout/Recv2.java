package com.roden.study.rabbitmq.demo.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.roden.study.rabbitmq.demo.util.ConnectionUtil;

public class Recv2 {
    private final static String QUEUE_NAME = "test_queue_work2";

    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws Exception{
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 声明队列
        String queueName =channel.queueDeclare(QUEUE_NAME, false, false, false, null).getQueue();
        //绑定到交换机
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        //同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        DeliverCallback dc = (consumerTag, delivery)->{
            String message=new String(delivery.getBody(),"UTF-8");
            System.out.println(" [Recv2] Received '"+message+"'");
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
        channel.basicConsume(queueName,false,dc,consumerTag->{});


    }
}
