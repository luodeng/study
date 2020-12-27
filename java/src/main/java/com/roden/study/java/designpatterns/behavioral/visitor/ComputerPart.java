package com.roden.study.java.designpatterns.behavioral.visitor;

public interface ComputerPart {
   public void accept(ComputerPartVisitor computerPartVisitor);
}