package com.roden.study.java.util.concurrent;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrieTest {
    @Test
    public void test(){
        CyclicBarrier cyclicBarrie=new CyclicBarrier(7,()->System.out.println("end"));
        for(int i=0;i<7;i++){
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
        }
    }
}
