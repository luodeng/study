package com.roden.study.java.designpatterns.creational.factory.simplefactory;

public class Circle implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Inside Circle::draw() method.");
   }
}