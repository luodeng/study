package com.roden.study.java.util.concurrent;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    @Test
    public void test() throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(10);

        for(int i=0;i<10;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        System.out.println(Thread.currentThread().getName()+"\t");
        countDownLatch.await();
        //同步计数器、阻塞当前线程，直到计数器的值为0
        System.out.println(Thread.currentThread().getName()+"\t");

    }
}
