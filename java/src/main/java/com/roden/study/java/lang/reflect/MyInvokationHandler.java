package com.roden.study.java.lang.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Roden on 2017/3/20.
 */
public class MyInvokationHandler implements InvocationHandler{
    public Object invoke(Object proxy, Method method,Object[] args){
        if(args!=null){
            for(Object obj:args){
                System.out.println(obj);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        InvocationHandler handler = new MyInvokationHandler();
        Person p=(Person) Proxy.newProxyInstance(Person.class.getClassLoader(),new Class[]{Person.class},handler);
        p.walk();
        p.sayHello("roden");
    }
}

interface Person{
    void walk();
    void sayHello(String name);
}
