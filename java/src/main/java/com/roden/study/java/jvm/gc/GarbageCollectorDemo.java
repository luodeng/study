package com.roden.study.java.jvm.gc;

/**
 * Parallel Scavenge 收集器是一个新生代收集器，采用复制算法，并且是多线程收集器；Parallel Scavenge 收集器的关注点与其他收集器不同，
 *      CMS等收集器的关注点是尽可能缩短垃圾收集时用户线程的停顿时间，而Parallel Scavenge 收集器的目标则是达到一个可控制的吞吐量（Throughput）。
 *      这里所谓的吞吐量是指CPU用于运行用户代码的时间与CPU总消耗时间的比值，既吞吐量 = 运行用户代码时间 / （运行用户代码时间 + 垃圾收集时间），
 *      虚拟机总共运行了100分钟，其中垃圾收集花掉了1分钟，那么吞吐量就是99%。
 *        停顿时间越短就越适合需要与用户交互的程序，良好的响应速度能提升用户体验，而高吞吐量则可以高效的利用CPU时间，尽快完成程序的运算任务，主要适合在后台运算而不需要太多交互的任务。
 *
 * Parallel Old 收集器是Parallel Scavenge 收集器的老年代版本，使用多线程和“标记-整理”算法。在注重吞吐量以及CPU资源敏感的场合，可以优先考虑Parallel Scavenge 加 Parallel Old 收集器
 *
 * 默认垃圾回收    UseParallelGC 即 Parallel Scavenge + Parallel Old
 *  java -XX:+PrintCommandLineFlags -version
 *      -XX:InitialHeapSize=267874624 -XX:MaxHeapSize=4285993984 -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelGC
 *      java version "1.8.0_211"
 *      Java(TM) SE Runtime Environment (build 1.8.0_211-b12)
 *      Java HotSpot(TM) 64-Bit Server VM (build 25.211-b12, mixed mode)
 *
 *  PSYoungGen->Parallel Scavenge
 *  ParOldGen->Parallel Old
 *
 *  java -XX:+PrintGCDetails -version
 *      java version "1.8.0_211"
 *      Java(TM) SE Runtime Environment (build 1.8.0_211-b12)
 *      Java HotSpot(TM) 64-Bit Server VM (build 25.211-b12, mixed mode)
 *      Heap
 *       PSYoungGen      total 76288K, used 3932K [0x000000076ad80000, 0x0000000770280000, 0x00000007c0000000)
 *        eden space 65536K, 6% used [0x000000076ad80000,0x000000076b157240,0x000000076ed80000)
 *        from space 10752K, 0% used [0x000000076f800000,0x000000076f800000,0x0000000770280000)
 *        to   space 10752K, 0% used [0x000000076ed80000,0x000000076ed80000,0x000000076f800000)
 *       ParOldGen       total 175104K, used 0K [0x00000006c0800000, 0x00000006cb300000, 0x000000076ad80000)
 *        object space 175104K, 0% used [0x00000006c0800000,0x00000006c0800000,0x00000006cb300000)
 *       Metaspace       used 2365K, capacity 4480K, committed 4480K, reserved 1056768K
 *        class space    used 255K, capacity 384K, committed 384K, reserved 1048576K
 *
 *
 * Serial Collector (复制算法，单线程）
 *      -XX:+UseSerialGC  (Eden空间不足)
 *
 * Parallel Collector
 *      -XX:+UseParNewGc  (Serial 多线程版本)
 *      -XX:+UseParallelGC  (清空整个Heap回收 压缩)
 *      -XX:+UseParallelOldGC  (清空部分Heap回收 压缩)
 *  CMS collector
 *      -XX:+UseConcMarkSweepGC  (最短回收停顿时间为目标,检查old区使用率进行回收)
 *
 *  Parallel Scavenge (吞吐量优先)
 *      -XX:+UseAdaptiveSizePolicy GC自适应的调节策略
 *              无需要设置定新生代的大小（-Xmn）、
 *              Eden与Survivor区的比例（-XX:SurivivorRatio）、
 *              晋升老年代对象年龄（-XX:PretenureSizeThreshold）等细节参数
 * @author Roden
 */
public class GarbageCollectorDemo {
    /**
     *  -Xms20M -Xmx20M -Xmn10M -XX:+UseParallelGC -XX:+PrintGCDetails
     * @param args
     */
    public static void main(String[] args) {
        int m=1024*1024;
        byte[] b=new byte[2*m];
        byte[] b2=new byte[2*m];
        byte[] b3=new byte[2*m];
        byte[] b4=new byte[2*m];
        byte[] b5=new byte[2*m];
        byte[] b6=new byte[2*m];
        byte[] b7=new byte[2*m];
    }
}
