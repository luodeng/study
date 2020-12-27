package com.roden.study.java.designpatterns.structural.facade;

public class Square implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Square::draw()");
   }
}