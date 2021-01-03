package com.roden.study.java.util.concurrent;

import java.util.concurrent.TimeUnit;

public class RunnableTest implements Runnable{
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("runnable");
    }

    public static void main(String[] args) {
        Runnable runnable=new RunnableTest();
        Thread thread=new Thread(runnable);

        //控制线程
        try {
            //改变线程优先级   必须在启动前设置
            thread.setPriority(Thread.MAX_PRIORITY);
            //设置为后台线程
            thread.setDaemon(true);

            thread.start();

            //让一个线程等待另一个线程完成
            thread.join();
            //线程让步
            Thread.yield();
            // 线程睡眠
            TimeUnit.SECONDS.sleep(10);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
