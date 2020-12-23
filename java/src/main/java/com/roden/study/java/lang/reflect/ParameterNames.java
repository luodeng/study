package com.roden.study.java.lang.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/*
  如果使用–parameters参数来编译这个类，参数的真实名字将会显示出来
  maven配置
  <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>3.1</version>
      <configuration>
          <compilerArgument>-parameters</compilerArgument>
          <source>1.8</source>
          <target>1.8</target>
      </configuration>
  </plugin>
*/
public class ParameterNames {
   public static void main(String[] 获取参数名) throws Exception {
       Method method = ParameterNames.class.getMethod( "main", String[].class );
       for( final Parameter parameter: method.getParameters() ) {
           System.out.println( "Parameter: " + parameter.getName() );
       }
   }
}