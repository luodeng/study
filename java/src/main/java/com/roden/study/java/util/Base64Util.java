package com.roden.study.java.util;

import org.junit.Test;

import java.io.*;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by Administrator on 2016/12/23.
 */
public class Base64Util {
    @Test
   public void test()throws Exception{
       // 编码
       String asB64 = Base64.getEncoder().encodeToString("some string".getBytes("utf-8"));
       System.out.println(asB64);    // 输出为: c29tZSBzdHJpbmc=
        // 解码
       byte[] asBytes = Base64.getDecoder().decode("c29tZSBzdHJpbmc=");
       System.out.println(new String(asBytes, "utf-8"));  // 输出为: some string


        String basicEncoded = Base64.getEncoder().encodeToString("subjects?abcd".getBytes("utf-8"));
        System.out.println("Using Basic Alphabet: " + basicEncoded);// 输出为:   Using Basic Alphabet: c3ViamVjdHM/YWJjZA==

        String urlEncoded = Base64.getUrlEncoder().encodeToString("subjects?abcd".getBytes("utf-8"));
        System.out.println("Using URL Alphabet: " + urlEncoded);// 输出为:   Using URL Alphabet: c3ViamVjdHM_YWJjZA==



        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < 10; ++t) {
            sb.append(UUID.randomUUID().toString());
        }

        byte[] toEncode = sb.toString().getBytes("utf-8");
        String mimeEncoded = Base64.getMimeEncoder().encodeToString(toEncode);
        System.out.println(mimeEncoded);
        // 输出为:
        /*
        NDU5ZTFkNDEtMDVlNy00MDFiLTk3YjgtMWRlMmRkMWEzMzc5YTJkZmEzY2YtM2Y2My00Y2Q4LTk5
                ZmYtMTU1NzY0MWM5Zjk4ODA5ZjVjOGUtOGMxNi00ZmVjLTgyZjctNmVjYTU5MTAxZWUyNjQ1MjJj
        NDMtYzA0MC00MjExLTk0NWMtYmFiZGRlNDk5OTZhMDMxZGE5ZTYtZWVhYS00OGFmLTlhMjgtMDM1
                ZjAyY2QxNDUyOWZiMjI3NDctNmI3OC00YjgyLThiZGQtM2MyY2E3ZGNjYmIxOTQ1MDVkOGQtMzIz
        Yi00MDg0LWE0ZmItYzkwMGEzNDUxZTIwOTllZTJiYjctMWI3MS00YmQzLTgyYjUtZGRmYmYxNDA4
                Mjg3YTMxZjMxZmMtYTdmYy00YzMyLTkyNzktZTc2ZDc5ZWU4N2M5ZDU1NmQ4NWYtMDkwOC00YjIy
        LWIwYWItMzJiYmZmM2M0OTBm
        */

    }
    @Test
    public void wrapping() throws IOException {
        String src = "This is the content of any resource read from somewhere" +
                " into a stream. This can be text, image, video or any other stream.";

        // 编码器封装OutputStream, 文件/tmp/buff-base64.txt的内容是BASE64编码的形式
        try (OutputStream os = Base64.getEncoder().wrap(new FileOutputStream("/tmp/buff-base64.txt"))) {
            os.write(src.getBytes("utf-8"));
        }

        // 解码器封装InputStream, 以及以流的方式解码, 无需缓冲
        // is being consumed. There is no need to buffer the content of the file just for decoding it.
        try (InputStream is = Base64.getDecoder().wrap(new FileInputStream("/tmp/buff-base64.txt"))) {
            int len;
            byte[] bytes = new byte[100];
            while ((len = is.read(bytes)) != -1) {
                System.out.print(new String(bytes, 0, len, "utf-8"));
            }
        }
    }

}
