package com.roden.study.java.lang.clazz;

import java.net.URL;

/**
 * Created by Roden on 2017/3/19.
 * java根类加载器
 */
public class BootstarpTest {
    public static void main(String[] args) {
        URL[] urls =sun.misc.Launcher.getBootstrapClassPath().getURLs();
        //URL[] urls =null;
        for(int i=0;i<urls.length;i++){
            System.out.println(urls[i].toExternalForm());
        }
    }
}
