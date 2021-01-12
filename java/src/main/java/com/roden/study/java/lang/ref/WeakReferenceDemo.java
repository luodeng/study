package com.roden.study.java.lang.ref;

import java.lang.ref.WeakReference;

/**
 * 弱引用
 *    系统垃圾回收机制运行时，不管内存是否足够，都会对对象占用的内存进行回收
 */
public class WeakReferenceDemo {
    public static void main(String[] args) {
        String str= new String("弱引用测试");
        WeakReference wr =new WeakReference(str);
        str=null;
        System.out.println(wr.get());
        System.gc();
        System.runFinalization();
        System.out.println(wr.get());

    }
}
