package com.roden.study.java.designpatterns.creational.factorymethod;

public class TeacherWorkFactory implements IWorkFactory {

    public Work getWork() {
        return new TeacherWork();
    }

}
