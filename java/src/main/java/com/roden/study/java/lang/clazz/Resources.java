package com.roden.study.java.lang.clazz;

import org.junit.Test;

/**
 * Created by Roden on 2017/3/11.
 */
public class Resources {
    @Test
    public void test1(){
        //查看class真实归属的jar包位置
        System.out.println(getClass().getClassLoader().getResource(getClass().getName().replace('.', '/') + ".class"));
    }
}
