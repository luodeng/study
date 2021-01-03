package com.roden.study.java.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author Roden
 */
public class CallableTest implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "success";
    }

    public static void main(String[] args) throws Exception {
        Callable<String> callable=new CallableTest();
        FutureTask<String> ft= new FutureTask(callable);
        Thread thread=new Thread(ft);
        thread.start();
        System.out.println(ft.get());
    }
}
