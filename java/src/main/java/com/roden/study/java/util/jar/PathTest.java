package com.roden.study.java.util.jar;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PathTest {
    @Test
    public void t() throws IOException {
        String jarName = "D:\\data\\repository\\com\\alibaba\\fastjson\\1.2.72\\fastjson-1.2.72.jar";
        JarFile jarFile = new JarFile(jarName);
        Enumeration<JarEntry> entrys = jarFile.entries();
        while (entrys.hasMoreElements()) {
            JarEntry jarEntry = entrys.nextElement();
            System.out.println(jarEntry.getName());
        }
    }
}
