package com.roden.study.java.designpatterns.creational.abstractfactory;

public class Rectangle implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Inside Rectangle::draw() method.");
   }
}