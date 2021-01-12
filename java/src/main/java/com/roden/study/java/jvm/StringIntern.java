package com.roden.study.java.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 *  可使用 Java VisualVM监控jvm

 *  设置最小堆内存和最大堆内存, 一样即不可扩展。
 *  关闭GC占用时间过长时会报的异常，否则还没报堆溢出会先报GC错误
 *  -Xmx5m -Xms5m -XX:-UseGCOverheadLimit
 *
 * 如果在jdk1.6环境下运行 同时限制方法区大小 将报OOM后面跟着PermGen space说明方法区OOM，即常量池在永久代
 * 如果是jdk1.7或1.8环境下运行 同时限制堆的大小  将报heap space 即常量池在堆中
 *
 *jdk1.7开始逐步去永久代。从String.interns()方法可以看出来
 * String.interns()
 * native方法:作用是如果字符串常量池已经包含一个等于这个String对象的字符串，则返回代表池中的这个字符串的String对象，
 * 在jdk1.6及以前常量池分配在永久代中。可通过 -XX:PermSize和-XX:MaxPermSize限制方法区大小。
 *
 *
 * 这边如果不设置UseGCOverheadLimit将报java.lang.OutOfMemoryError: GC overhead limit exceeded，
 * 这个错是因为GC占用了多余98%（默认值）的CPU时间却只回收了少于2%（默认值）的堆空间。
 * 目的是为了让应用终止，给开发者机会去诊断问题。一般是应用程序在有限的内存上创建了大量的临时对象或者弱引用对象，从而导致该异常。
 * 虽然加大内存可以暂时解决这个问题，但是还是强烈建议去优化代码，后者更加有效，也可通过UseGCOverheadLimit避免[不推荐，这里是因为测试用，并不能解决根本问题]

 */
public class StringIntern {
    // 运行如下代码探究运行时常量池的位置
    public static void main(String[] args) throws Throwable {
        //用list保持着引用 防止full gc回收常量池
        List<String> list = new ArrayList<String>();
        int i = 0;
        while(true){
            list.add(String.valueOf(i++).intern());
        }
    }
}
