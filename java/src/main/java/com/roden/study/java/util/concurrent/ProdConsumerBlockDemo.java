package com.roden.study.java.util.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
class MyResource{
    //默认开启，进行生产消费
    private volatile boolean flag=true;
    private AtomicInteger atomicInteger=new AtomicInteger();
    BlockingQueue<String> blockingQueue=null;
    // 传接口不传类
    public MyResource(BlockingQueue<String> blockingQueue){
        this.blockingQueue=blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }
    public void myProd() throws Exception{
        String data;
        boolean retValue=false;
        while (flag){
            data=atomicInteger.incrementAndGet()+"";
            retValue=blockingQueue.offer(data,2L, TimeUnit.SECONDS);
            if(retValue){
                System.out.println(Thread.currentThread().getName()+"\t插入队列"+data+"成功");
            }else {
                System.out.println(Thread.currentThread().getName()+"\t插入队列"+data+"失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t大老板叫停了flag=false生产结束");
    }
    public void myConsumer() throws Exception{
        String result;
        while (flag){
            result=blockingQueue.poll(2L, TimeUnit.SECONDS);
            if(null==result||"".equalsIgnoreCase(result)){
                flag=false;
                System.out.println(Thread.currentThread().getName()+"\t超过两秒未取到消费退出");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t消费队列"+result+"成功");
        }
    }
    public void stop()throws Exception{
        this.flag=false;
    }
}
public class ProdConsumerBlockDemo{
    public static void main(String[] args) {
        MyResource myResource=new MyResource(new ArrayBlockingQueue<>(10));
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t生产者启动");
            try {
                myResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"prod").start();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t消费者启动");
            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"consumer").start();

        try{
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName()+"\t5秒时间到大老板叫停");
            myResource.stop();
            TimeUnit.SECONDS.sleep(5);
        }catch (Exception e){

        }

    }
}
