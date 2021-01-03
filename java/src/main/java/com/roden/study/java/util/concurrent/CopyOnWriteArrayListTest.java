package com.roden.study.java.util.concurrent;

import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 写时复制
 * 读写分离
 */
public class CopyOnWriteArrayListTest {
    @Test
    public void t(){
        List list=new CopyOnWriteArrayList();
        //底层实现自CopyOnWriteArrayList
        Set set =new CopyOnWriteArraySet();
    }
}
