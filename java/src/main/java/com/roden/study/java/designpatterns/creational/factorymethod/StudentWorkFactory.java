package com.roden.study.java.designpatterns.creational.factorymethod;

public class StudentWorkFactory implements IWorkFactory {

    public Work getWork() {
        return new StudentWork();
    }

}
