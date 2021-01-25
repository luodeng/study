package com.roden.study.java.io;

import org.junit.Test;

import java.io.*;

public class FileReaderTest {
    public static void main(String[] args) throws IOException {
        StringBuffer str=new StringBuffer();
        char[] buf=new char[1024];
        FileReader fr=new FileReader("D:/fd.txt");
        while (fr.read(buf)>0){
            str.append(buf);
        }
        System.out.println(str.toString());
    }

    @Test
    public void t2() throws IOException {
        File file=new File("/temp/path.txt");
        OutputStream out=new FileOutputStream(file);
        out.write("Hello World".getBytes());
        out.close();
    }

    public String getErrorInfoFromException(Exception e) {
        PrintWriter pw=null;
        StringWriter sw =null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e1) {
            return "ErrorInfoFromException";
        }finally {
            try{
                sw.close();
                pw.close();
            }catch (Exception e2){
                return "System Error";
            }
        }
    }

}
