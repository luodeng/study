package com.roden.study.java.designpatterns.creational.abstractfactory;

public class Blue implements Color {
 
   @Override
   public void fill() {
      System.out.println("Inside Blue::fill() method.");
   }
}