package com.roden.study.java.nio.file;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemo {
    public static void main(String[] args) {
        //以当前路径来创建对象
        Path path = Paths.get(".");
        System.out.println("path里包含的路径数量:"+path.getNameCount());
        System.out.println("path的根路径:"+path.getRoot());
        //获取path的绝对路径
        Path absolutePath=path.toAbsolutePath();
        System.out.println(absolutePath);
        System.out.println("absolutePath的根路径:"+absolutePath.getRoot());
        System.out.println("absolutePath里包含的路径数量:"+absolutePath.getNameCount());
        System.out.println(absolutePath.getName(2));
        //以多个string来创建Path对象
        Path path2=Paths.get("d:","project","github");
        System.out.println(path2);
    }
}
