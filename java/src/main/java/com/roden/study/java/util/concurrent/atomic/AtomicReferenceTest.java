package com.roden.study.java.util.concurrent.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicReferenceTest {
    /**
     * 原子更新对象
     */
    @Test
    public void object() {
        AtomicReference<User> atomicReference=new AtomicReference<>();
        User zhangSan=new User("zhangsan",22);
        User liSi=new User("lisi",32);
        atomicReference.set(zhangSan);
        System.out.println(atomicReference.compareAndSet(zhangSan,liSi)+"\t"+atomicReference.get());
        System.out.println(atomicReference.compareAndSet(zhangSan,liSi)+"\t"+atomicReference.get());
    }
    /**
     * 原子更新对象
     * 会有ABA问题
     */
    @Test
    public void object2() throws InterruptedException {
        AtomicReference<Integer> atomicReference=new AtomicReference<>(100);
        new Thread(()->{
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);

        },"线程1").start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100,2019)+"\t"+atomicReference.get());
        }, "线程2").start();
        Thread.sleep(2000);
    }

    /**
     * 原子更新对象
     * 解决ABA问题
     */
    @Test
    public void object32() throws InterruptedException {
        AtomicStampedReference<Integer> atomicStampedReference=new AtomicStampedReference<>(100,1);
        new Thread(()->{
            int stmp=atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t"+stmp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(100, 101, stmp, stmp + 1));
            System.out.println(atomicStampedReference.getReference()+"\t"+atomicStampedReference.getStamp());

        },"线程1").start();
        new Thread(()->{
            int stmp=atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t"+stmp);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(100,101,stmp,stmp+1));
            System.out.println(atomicStampedReference.getReference()+"\t"+atomicStampedReference.getStamp());
        }, "线程2").start();
        Thread.sleep(5000);
    }

}
@Data
@AllArgsConstructor
class User{
    String userNmae;
    int age;
}