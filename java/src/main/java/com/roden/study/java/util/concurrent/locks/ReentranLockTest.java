package com.roden.study.java.util.concurrent.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
}
