package com.roden.study.java.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 快速失败(fail-fast)与安全失败(fail-safe)
 *
 *在Collection集合的各个类中，有线程安全和线程不安全这2大类的版本。
 *   对于线程不安全的类，并发情况下可能会出现fail-fast情况；而线程安全的类，可能出现fail-safe的情况。
 *
 *   并发修改
 * 当一个或多个线程正在遍历一个集合Collection的时候（Iterator遍历），而此时另一个线程修改了这个集合的内容（如添加，删除或者修改）。这就是并发修改的情况。
 *
 * fail-fast快速失败
 * fail-fast机制：当遍历一个集合对象时，如果集合对象的结构被修改了，就会抛出ConcurrentModificationExcetion异常。
 */
public class FailFastAndFailSafeDemo {

    /**
     * fail-fast
     * 在单线程的情况下，如果使用Iterator对象遍历集合对象的过程中，修改了集合对象的结构。
     *
     * 在多线程环境下，如果对集合对象进行并发修改，那么就会抛出ConcurrentModificationException异常。
     *
     * 注意，迭代器的快速失败行为无法得到保证，因为一般来说，不可能对是否出现不同步并发修改做出任何硬性保证。快速失败迭代器会尽最大努力抛出 ConcurrentModificationException。
     * 因此，为提高这类迭代器的正确性而编写一个依赖于此异常的程序是错误的做法，迭代器的快速失败行为应该仅用于检测 bug
     *
     * 调用next方法时，第一步就是检查modCount值和迭代器内部的expectedModCount值是否相等，迭代过程中发生了修改，但之后没有调用next()迭代，该异常就不会抛出了
     */
    @Test
    public void test1(){
        List<String> list= new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // 1.iterator迭代，抛出ConcurrentModificationException异常
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            System.out.println(s);
            // 修改集合结构
            if ("b".equals(s)) {
                list.remove(s);
            }
        }

        // 2.foreach迭代，抛出ConcurrentModificationException异常
        for (String s : list) {
            System.out.println(s);
            // 修改集合结构
            if ("b".equals(s)) {
                list.remove(s);
            }
        }
        // 要想避免抛出异常，应该使用Iterator对象的remove()方法。
        // 3.iterator迭代，使用iterator.remove()移除元素不会抛出异常
        Iterator<String> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            String s = iterator2.next();
            System.out.println(s);
            // 修改集合结构
            if ("b".equals(s)) {
                iterator2.remove();
            }
        }
    }

    /**
     * Fail-Safe 迭代的出现，是为了解决fail-fast抛出异常处理不方便的情况。fail-safe是针对线程安全的集合类。
     * 并发容器的iterate方法返回的iterator对象，内部都是保存了该集合对象的一个快照副本
     * 你可以并发读取，不会抛出异常，但是不保证你遍历读取的值和当前集合对象的状态是一致的！这就是安全失败
     * java.util.concurrent 包中集合的迭代器，如 ConcurrentHashMap, CopyOnWriteArrayList等默认为都是Fail-Safe
     *
     */
    @Test
    public void test2() {
        List<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        // 1.foreach迭代，fail-safe，不会抛出异常
        for (String s : list) {
            System.out.println(s);
            if ("a".equals(s)) {
                list.remove(s);
            }
        }

        // 2.iterator迭代，fail-safe，不会抛出异常
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            System.out.println(s);
            if ("b".equals(s)) {
                list.remove(s);
            }
        }
    }

}
