package com.roden.study.java.util.function;

import org.junit.Test;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 函数式接口在java中是指:有且仅有一个抽象方法的接口函数式接口，即适用于函数式编程场景的接口。
 * 而java中的函数式编程体现就是Lambda，所以函数式接口就是可以适用于Lambda使用的接口。
 * 只有确保接口中有且仅有一个抽象方法，Java中的Lambda才能顺利地进行推导。
 *
 *
 * @author luo_d
 */
public class FunctionalInterfaceTest {
    @Test
    public void lambda () {
        //省略形参参数类型
        //只有一个参数，省略参数括号
        //只有一条语句，省略大括号
        Arrays.asList( "a", "b", "d" ).forEach((String e)->{
            System.out.println(e);
        });
        Arrays.asList( "a", "b", "d" ).forEach(e->System.out.println(e));

        //省略返回语句
        Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> {
            int result = e1.compareTo( e2 );
            return result;
        } );
        Arrays.asList( "a", "b", "d" ).sort( (e1, e2 ) -> e1.compareTo( e2 ) );

        //构建List
        List<Integer> list=Stream.iterate(2, n->n+1).limit(10).collect(Collectors.toList());
        // list.forEach(obj->System.out.println(obj));
        list.forEach(System.out::println);

        //创建线程
        Runnable r=()->{
            for (int i=0;i<100;i++){
                System.out.println(i);
            }
        };
        new Thread(r).start();
        Thread.yield();
    }

    @FunctionalInterface
    interface Converter {
        Integer convert(String from);
    }
    @FunctionalInterface
    interface MyTest {
        String test(String a,int b,int c);
    }
    @FunctionalInterface
    interface YouTest {
        JFrame win(String title);
    }

    @Test
    public void test2(){
        //引用类方法
        //Converter converter=from -> Integer.valueOf(from);
        Converter converter1=Integer::valueOf;
        Integer val=converter1.convert("99");
        System.out.println(val);

        //引用特定对象的实例方法
        //Converter converter2=from -> "fkit.org".indexOf("from");
        Converter converter2="fkit.org"::indexOf;
        Integer value=converter2.convert("it");
        System.out.println(value);

        //引用某类对象的实例方法
        //MyTest mt=(a, b, c) -> a.substring(b,c);
        MyTest mt=String::substring;
        String str=mt.test("Java I Love you",2,9);
        System.out.println(str);

        // 引用构造器
        //YouTest yt=(String a)->new JFrame(a);
        YouTest yt=JFrame::new;
        JFrame jf=yt.win("我的窗口");
        System.out.println(jf);
    }

    /**
     * Predicate是一个断言型接口，用于做判断操作，所以抽象方法返回的是Boolean
     */
    @Test
    public void predicate(){
        Predicate<String> p =(str)->str.contains("a");
        System.out.println(p.test("abc"));
    }

    /**
     * Function接口为函数型接口，该接口的抽象方法接收一个参数并且做一些处理然后返回
     */
    @Test
    public void function(){
        Function<String,Integer> f =(x)->x.length();
        System.out.println(f.apply("abc"));
    }

    /**
     * Consumer是消费型接口。Consumer表示执行在单个参数上面的操作，但没有返回值的（正如消费有去无回）
     */
    @Test
    public void consumer(){
        Consumer<String> consumer =(x)->System.out.println(x);
        consumer.accept("abc");
    }

    /**
     * Supplier接口为供给型接口。该接口不接受任何参数，返回一个任意泛型的值
     */
    @Test
    public void supplier(){
        Supplier<String> supplier =()-> "abc";
        System.out.println(supplier.get());
    }
}
