package com.roden.study.java.util.spi;

public class AliyunCDN implements UploadCDN {
    @Override
    public void upload(String url) {
        System.out.println("upload to aliyun cdn");
    }
}
