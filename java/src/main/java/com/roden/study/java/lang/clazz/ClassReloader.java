package com.roden.study.java.lang.clazz;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class ClassReloader extends ClassLoader{
    private String classPath;
    String className="com.roden.study.java.lang.clazz.Hello";
    public ClassReloader(String classPath){
        this.classPath=classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getData(name);
        if(classData==null){
            throw new ClassNotFoundException();
        }else {
            return defineClass(className,classData,0,classData.length);
        }
    }
    private byte[] getData(String className){
        String path=classPath+className;
        try{
            InputStream is=new FileInputStream(path);
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            byte[] buffer=new byte[2048];
            int num=0;
            while ((num=is.read(buffer))!=-1){
                baos.write(buffer,0,num);
            }
            return baos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        try {
            String path=ClassReloader.class.getResource("").getPath();

            ClassReloader classReloader = new ClassReloader(path);
            Class r = classReloader.findClass("Hello.class");
            System.out.println(r.newInstance());

            ClassReloader classReloader2 = new ClassReloader(path);
            Class r2 = classReloader2.findClass("Hello.class");
            System.out.println(r2.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
