package com.roden.study.rabbitmq.boot.simple;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MqSenderTest {
    @Autowired
    private AmqpTemplate amqpTemplate;
    /**
     * 生产者将消息直接发送到队列
     * @throws Exception
     */
    @Test
    public void send() throws Exception {
        amqpTemplate.convertAndSend("myQueue", "hello saysky, now is " + LocalDateTime.now());
    }
    /**
     * 生产者将消息发送到Exchange
     * @throws Exception
     */
    @Test
    public void sendComputer() throws Exception {
        amqpTemplate.convertAndSend("computerOrder", "computer", "hello saysky, now is " + new Date());
    }
    /**
     * 生产者将消息发送到Exchange
     * @throws Exception
     */
    @Test
    public void sendFruit() throws Exception {
        amqpTemplate.convertAndSend("fruitOrder", "fruit", "hello saysky, now is " + new Date());
    }
}