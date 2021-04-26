package com.roden.study.java.lang;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @Author luodeng
 * @create 2021/4/22 19:48
 */
public class StringTest {
    @Test
    public void split(){
        //末尾分隔符全部忽略
        System.out.println(":ab:cd:ef::".split(":").length);
        //不忽略任何一个分隔符
        System.out.println(":ab:cd:ef::".split(":",-1).length);
        //最前面的和末尾的分隔符全部都忽略,apache commons
        System.out.println(StringUtils.split(":ab:cd:ef::",":").length);
        //不忽略任何一个分隔符 apache commons
        System.out.println(StringUtils.splitPreserveAllTokens(":ab:cd:ef::",":").length);

    }
}
