package com.roden.study.java.util.concurrent;

public class RunnableTest implements Runnable{
    @Override
    public void run() {
        System.out.println("runnable");
    }

    public static void main(String[] args) {
        Runnable runnable=new RunnableTest();
        Thread thread=new Thread(runnable);
        thread.start();
    }
}
