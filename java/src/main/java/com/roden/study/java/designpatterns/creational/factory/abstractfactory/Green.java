package com.roden.study.java.designpatterns.creational.factory.abstractfactory;

public class Green implements Color {
 
   @Override
   public void fill() {
      System.out.println("Inside Green::fill() method.");
   }
}