package com.roden.study.java.util.spi;

import org.junit.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * SPI全称Service Provider Interface，是Java提供的一套用来被第三方实现或者扩展的接口，它可以用来启用框架扩展和替换组件。 SPI的作用就是为这些被扩展的API寻找服务实现。
 * 将装配的控制权移到了程序之外：jdbc 驱动加载
 */
public class ServiceLoaderTest {
    @Test
    public void test(){
        ServiceLoader<UploadCDN> serviceLoader=ServiceLoader.load(UploadCDN.class);
        Iterator<UploadCDN> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            UploadCDN uploadCDN =  iterator.next();
            uploadCDN.upload("hello world");
        }
    }
}
