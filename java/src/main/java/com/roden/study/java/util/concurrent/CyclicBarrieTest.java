package com.roden.study.java.util.concurrent;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

public class CyclicBarrieTest {
    @Test
    public void test(){
        CyclicBarrier cyclicBarrie=new CyclicBarrier(7,()->System.out.println("end"));
        IntStream.range(1,8).forEach(i->{
            new Thread(()->{
                System.out.println(Thread.currentThread().getName());
                try {
                    cyclicBarrie.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        });
    }
}
