package com.roden.study.java.designpatterns.structural.facade;

public class Rectangle implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Rectangle::draw()");
   }
}