package com.roden.study.java.nio.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilesDemo {
    public static void main(String[] args) throws IOException {
        Files.copy(Paths.get("FilesDemo.class"),new FileOutputStream("a.txt"));
        System.out.println(Files.isHidden(Paths.get("FilesDemo.java")));
    }
}
