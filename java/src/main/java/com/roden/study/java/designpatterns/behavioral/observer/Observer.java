package com.roden.study.java.designpatterns.behavioral.observer;

public abstract class Observer {
   protected Subject subject;
   public abstract void update();
}