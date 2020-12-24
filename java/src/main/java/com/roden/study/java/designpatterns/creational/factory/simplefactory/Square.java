package com.roden.study.java.designpatterns.creational.factory.simplefactory;

public class Square implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Inside Square::draw() method.");
   }
}