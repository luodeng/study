package com.roden.study.java.lang.ref;

import java.lang.ref.SoftReference;

/**
 * 软引用
 *  内存不足时，可能会回收
 */
public class SoftReferenceDemo {
    public static void main(String[] args) {
        String str= new String("弱引用测试");
        SoftReference softReference=new SoftReference(str);
        str=null;
    }
}
