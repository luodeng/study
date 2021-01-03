package com.roden.study.java.util.concurrent;

import org.openjdk.jol.info.ClassLayout;

/**
 * 创建对象
 *      申请内存  (半初始化，实例为默认值)
 *      初始化（调用构造方法）
 *      建立关联  引用指向
 *
 * DCL单例 (volatile 保持线程可见 禁止指令重排序)
 *
 * 对象字节码
 *      Mark Word  (8字节  锁信息)
 *      class pointer （4字节 类型指针 指向对应 *.class 对象）
 *      实例数据(成员变量)
 *      对齐 （填充保证为被8整除）
 *
 *  无锁->偏向锁->轻量级->重量级
 *  轻量级锁 Mark Word中指向线程栈中lock record的指针
 *
 *  --XX:-UseBiasedLocking=false 关闭偏向锁
 *  Lock Record中会记录Mark Word中老值
 *  可重入  同一个对象锁多层
 *
 *  CAS (Compare And Swap)   本质 汇编（lock cmpxchg 指令 ) 锁定北桥信号
 *
 */
public class SynchronizedTest {
    private Integer value;
    private String name;
    private Long  age;
    private Long  age2;
    private Long  age3;
    public SynchronizedTest(){
        this.value=8;
    }

    @org.junit.Test
    public void makeWord(){
        Object object = new Object();
        //无锁  0 01
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
        //修改 Mark Word
        synchronized (object) {
            //轻量级锁 00
            this.value=new Integer(8);
            System.out.println(ClassLayout.parseInstance(object).toPrintable());
        }
    }

    @org.junit.Test
    public void biasedLock() throws InterruptedException {
        //偏向锁 jvm启动4秒后开启   (jdk内部类无需要偏向)
        Thread.sleep(5000);
        Object object = new Object();
        // 1 01 偏向锁
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
        //修改 Mark Word
        synchronized (object) {
            // 1 01 偏向锁
            System.out.println(ClassLayout.parseInstance(object).toPrintable());
        }
    }
}