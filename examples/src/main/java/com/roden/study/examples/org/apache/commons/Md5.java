package com.roden.study.examples.org.apache.commons;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.FileInputStream;


/**
 * Created by Administrator on 2017/3/9.
 */
public class Md5 {
    @Test
    public void testMd5(){
        String path=Md5.class.getResource("Md5.class").getFile();
        FileInputStream fis= null;
        String md5 =null;
        try {
            fis = new FileInputStream(path);
            md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
        } catch (Exception e) {
            e.printStackTrace();
        }
        IOUtils.closeQuietly(fis);
        System.out.println("file MD5:"+md5);
        System.out.println("String MD5:"+DigestUtils.md5Hex("123456"));
    }
}
