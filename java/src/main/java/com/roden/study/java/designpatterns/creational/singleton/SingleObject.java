package com.roden.study.java.designpatterns.creational.singleton;
/**
   经验之谈：
      一般情况下，不建议使用第 1 种和第 2 种懒汉方式，建议使用第 3 种饿汉方式。
      只有在要明确实现 lazy loading 效果时，才会使用第 5 种登记方式。
      如果涉及到反序列化创建对象时，可以尝试使用第 6 种枚举方式。
      如果有其他特殊的需求，可以考虑使用第 4 种双检锁方式。
 */
public class SingleObject {

   //创建 SingleObject 的一个对象
   private static SingleObject instance = new SingleObject();

   //让构造函数为 private，这样该类就不会被实例化
   private SingleObject(){}

   //获取唯一可用的对象
   public static SingleObject getInstance(){
      return instance;
   }

   public void showMessage(){
      System.out.println("Hello World!");
   }
}