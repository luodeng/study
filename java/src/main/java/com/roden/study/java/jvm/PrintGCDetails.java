package com.roden.study.java.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -XX:+PrintGCDetails
 */

public class PrintGCDetails {
    public static void main(String[] args) {
        for(int i = 0; i < 10000; i++) {
            List<String> list = new ArrayList<>();
            list.add("aaaaaaaaaaaaa");
        }
        System.gc();
    }
}