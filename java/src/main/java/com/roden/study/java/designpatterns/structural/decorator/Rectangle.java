package com.roden.study.java.designpatterns.structural.decorator;

public class Rectangle implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Shape: Rectangle");
   }
}