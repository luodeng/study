package com.roden.study.examples.org.apache.commons;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by Roden on 2017/3/11.
 */
public class Test {
    @org.junit.Test
    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("luodeng"));
        System.out.println(new String(Base64.encodeBase64("luodeng".getBytes())));
        byte[] b = Base64.encodeBase64("luodeng".getBytes());

        System.out.println(new String(Base64.decodeBase64(b)));
    }

}
