package com.roden.study.java.lang.clazz;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * Created by Roden on 2017/3/19.
 */
public class ClassLoaderPropTest {
    public static void main(String[] args) throws IOException {
        //系统类加载器
        ClassLoader systemLoader=ClassLoader.getSystemClassLoader();
        System.out.println(systemLoader);
        Enumeration<URL> eml=systemLoader.getResources("");
        while (eml.hasMoreElements()){
            System.out.println(eml.nextElement());
        }
        //扩展类加载器
        ClassLoader extensionLader=systemLoader.getParent();
       System.out.println(extensionLader);
       System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(extensionLader.getParent());

    }
}
