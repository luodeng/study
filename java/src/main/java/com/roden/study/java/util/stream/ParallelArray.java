package com.roden.study.java.util.stream;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class ParallelArray {
    public static void main( String[] args ) {
        long[] arrayOfLong = new long [ 20000 ];
        //随机数组
        Arrays.parallelSetAll( arrayOfLong,index -> ThreadLocalRandom.current().nextInt( 1000000 ) );
        Arrays.stream( arrayOfLong ).limit( 10 ).forEach(i -> System.out.print( i + " " ) );
        System.out.println();
        //排序
        Arrays.parallelSort( arrayOfLong );
        Arrays.stream( arrayOfLong ).limit( 10 ).forEach(i -> System.out.print( i + " " ));
        System.out.println();
    }
}
