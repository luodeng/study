package com.roden.study.java.lang.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用 ( 类似没有引用）
 *  不能单独使用，主要用于跟踪对象垃圾回收状态
 */
public class PhantomReferenceDemo {
    public static void main(String[] args) {
        String str= new String("弱引用测试");
        ReferenceQueue rq=new ReferenceQueue();
        PhantomReference pr =new PhantomReference(str,rq);
        str=null;
        System.out.println(pr.get());
        System.gc();
        System.runFinalization();
        System.out.println(rq.poll()==pr);
    }
}
