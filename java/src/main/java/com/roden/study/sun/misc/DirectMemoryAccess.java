package com.roden.study.sun.misc;

import java.lang.reflect.Field;
  
import sun.misc.Unsafe;  
  
/**
 * 直接操作内存
 * - allocateMemory reallocateMemory freeMemory 分别用于分配内存，扩充内存和释放内存
 * 直接生成类实例
 * - allocateInstance
 * 直接操作类或者实例变量
 * - objectFieldOffset
 * - getInt
 * -getObject
 * CAS相关操作
 * - compareAndSwapObject
 *
 * jdk11.0可以直接getUnsafe()获取到，jdk8是return null:
 *
 *
 * @author Roden
 */
public class DirectMemoryAccess {
  
    public static void main(String[] args) {  
  
        /* 
         * Unsafe的构造函数是私有的，不能通过new来获得实例。 
         *  
         *  通过反射来获取 
         */  
        Unsafe unsafe = null;  
        Field field = null;  
        try {  
            field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");  
            /* 
             * private static final Unsafe theUnsafe = new Unsafe(); 
             *  
             * 因为field的修饰符为 private static final, 
             * 需要将setAccessible设置成true，否则会报java.lang.IllegalAccessException 
             */  
            field.setAccessible(true);  
            unsafe = (Unsafe) field.get(null);  
        } catch (SecurityException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (NoSuchFieldException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IllegalArgumentException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
        long oneHundred = 100;  
        byte size = 1;  
  
        /* 
         * 调用allocateMemory分配内存
         * 分配内存的方法：相当于C语言中的memoryAllocation分配内存的方法。C释放内存的方法free()
         *      语言	分配内存	          释放内存
                C	    memoryAllocation()	   free()
                C++	        new	               delete
         */  
        long memoryAddress = unsafe.allocateMemory(size);  
  
        /* 
         * 将100写入到内存中 
         */  
        unsafe.putAddress(memoryAddress, oneHundred);  
  
        /* 
         * 内存中读取数据  
         */  
        long readValue = unsafe.getAddress(memoryAddress);  
  
        System.out.println("Val : " + readValue);  
    }  
}  