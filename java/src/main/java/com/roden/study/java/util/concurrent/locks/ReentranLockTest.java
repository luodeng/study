package com.roden.study.java.util.concurrent.locks;

import org.junit.Test;

import java.util.concurrent.locks.*;

/**
 * 独占锁
 *
 * 共享锁
 *  读-共存
 *  读写-不能共存
 *  写-不能共存
 */
public class ReentranLockTest {
    public void t2(){
        //默认非公平 传true公平 fifo
        Lock lock=new ReentrantLock(true);
        //ReadLock  WriteLock
        ReadWriteLock readWriteLock=new ReentrantReadWriteLock(true);
    }
    @Test
    public void t3(){
        int value=1;
        Lock lock=new ReentrantLock();
        Condition condition_1=lock.newCondition();
        Condition condition_2=lock.newCondition();
        Condition condition_3=lock.newCondition();
        lock.lock();
        try {
            while (value==1){
                condition_1.wait();
            }
            condition_2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
