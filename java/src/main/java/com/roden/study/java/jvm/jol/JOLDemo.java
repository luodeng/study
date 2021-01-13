package com.roden.study.java.jvm.jol;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class JOLDemo {
    @Test
    public void test1() {
        A a = new A();
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }
    @Test
    public void test2() throws Exception {
        A a = new A();
        System.out.println("before hash");
        //未计算hashcode之前的对象头
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        //JVM计算的hashcode
        System.out.println("JVM calculated hashcode---------0x"+Integer.toHexString(a.hashCode()));
        calcHash(a);
        System.out.println("after hash");
        //计算hashcode之后的对象头
        System.out.println(ClassLayout.parseInstance(a).toPrintable());;
    }

    public static void calcHash(Object object) throws NoSuchFieldException, IllegalAccessException {
        // 手动计算HashCode
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        long hashCode = 0;
        //！！！注意，由于是小端存储，我们将从后往前计算
        // 数据的高字节保存在内存的高地址中，而数据的低字节保存在内存的低地址中
        for (long index = 7; index > 0; index--){
            //取Mark Word中的每一个Byte进行计算
            hashCode |= (unsafe.getByte(object, index) & 0xFF) << ((index -1) *8);
        }
        String code = Long.toHexString(hashCode);
        System.out.println("calculated hashcode ---0x" + code);
    }
}
class A{
  boolean flag;
}