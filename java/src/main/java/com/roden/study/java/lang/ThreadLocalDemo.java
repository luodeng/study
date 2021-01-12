package com.roden.study.java.lang;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 1、在进行对象跨层传递的时候，使用ThreadLocal可以避免多次传递，打破层次间的约束。
 * 2、线程间数据隔离
 * 3、进行事务操作，用于存储线程事务信息。
 * 4、数据库连接，Session会话管理。
 *
 * 由于ThreadLocalMap的生命周期跟Thread一样长，如果没有手动删除对应key就会导致内存泄漏，而不是因为弱引用
 *
 */

public class ThreadLocalDemo {
    public static void main(String[] args) {
        ThreadLocal<String> local=new ThreadLocal<>();
        Random random=new Random();
        IntStream.range(0,5).forEach(a->new Thread(()->{
            local.set(a+" "+random.nextInt(10));
            System.out.println("线程和local值分别是 "+local.get());
            try {
                TimeUnit.SECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start());
    }
}
