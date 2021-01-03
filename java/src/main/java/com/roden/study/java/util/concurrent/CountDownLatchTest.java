package com.roden.study.java.util.concurrent;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
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
        System.out.println(Thread.currentThread().getName()+"\t");

    }
}
