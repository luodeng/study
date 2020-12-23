package com.roden.study.java.lang.clazz;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Roden on 2017/3/19.
 */
public class URLClassLoaderTest {
    public static void main(String[] args) throws Exception {
        //.jar  ftp http
        URL[] urls={new URL("file:Hello.class")};
        URLClassLoader ucl=new URLClassLoader(urls);
        Object obj=ucl.loadClass("jdk.lang.clazz.Hello").newInstance();
        for(Method method:obj.getClass().getDeclaredMethods()){
            System.out.println(method.getName());
        }
    }
}
