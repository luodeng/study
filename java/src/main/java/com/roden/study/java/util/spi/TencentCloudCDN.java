package com.roden.study.java.util.spi;

public class TencentCloudCDN implements UploadCDN {
    @Override
    public void upload(String url) {
        System.out.println("upload to tencent cloud cdn");
    }
}
