package com.roden.study.java.util.concurrent;

public class ThreadTest extends Thread{
    @Override
    public void run(){
        System.out.println("thread");
    }

    public static void main(String[] args) {
        Thread thread=new ThreadTest();
        thread.start();
    }
}
