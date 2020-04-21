package com.roden.study.redis.util;

import java.io.FileNotFoundException;
import java.io.IOException;  
import java.io.InputStream;  
import java.util.Properties;  
  
public class ProPertiesUtils {
    public static Properties getProPerties(String path) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = Object.class.getResourceAsStream(path);
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return properties;
    }

    public static String getValue(String path,String key) {
        Properties properties=getProPerties(path);
        if(properties.containsKey(key)){
            return properties.getProperty(key);
        }
        return "";
    }
      
    public static void main(String[] args) {  
        System.out.println(ProPertiesUtils.getValue("/jedis.properties", "jedis.host"));
    }  
}  