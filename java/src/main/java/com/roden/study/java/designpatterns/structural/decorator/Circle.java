package com.roden.study.java.designpatterns.structural.decorator;

public class Circle implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Shape: Circle");
   }
}