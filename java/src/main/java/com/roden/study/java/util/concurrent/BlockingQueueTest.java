package com.roden.study.java.util.concurrent;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;

/**
 * 1 队列
 * 2、阻塞队列      空消费者阻塞 满生产者阻塞
 *
 * 线程池
 * 消息中间件
 * @author Roden
 */
public class BlockingQueueTest {
    @Test
    public void blockingQueueTest(){
        // 异常 add remove element
        // 特殊值 offer poll peek
        // 阻塞 put take
        // 超时 offer(e,time,unit) poll(time,unit)
        BlockingQueue arrayBlockingQueue=new ArrayBlockingQueue(3);
        System.out.println(arrayBlockingQueue.add("a"));
        arrayBlockingQueue.add("b");
        arrayBlockingQueue.add("c");
        //arrayBlockingQueue.add("d"); 异常
        //队首元素
        System.out.println(arrayBlockingQueue.element());
        System.out.println(arrayBlockingQueue.peek());

        //慎用 默认大小为Integer.MAX_VALUE
        BlockingQueue linkedBlockingDeque=new LinkedBlockingQueue(10);
        //单元素队列
        SynchronousQueue synchronousQueue=new SynchronousQueue(true);
    }
    @Test
    public void synchronousQueueTest(){
        SynchronousQueue synchronousQueue=new SynchronousQueue(true);
        new Thread(()->{

        },"A").start();
        new Thread(()->{

        },"A").start();
    }
}
