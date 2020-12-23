package com.roden.study.java.time;

import org.junit.Test;

import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/9.
 */
public class DateTimeUtilTest {
    @Test
    public void test( ){
       System.out.println(DateTimeUtil.DateTime2LocalDateTime(new Date()));
        System.out.println( ZoneId.systemDefault());
    }
}
