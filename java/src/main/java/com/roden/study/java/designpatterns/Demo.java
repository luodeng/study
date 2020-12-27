package com.roden.study.java.designpatterns;

import org.junit.Test;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.ElementVisitor;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.ResourceBundle;

public class Demo {
    @Test
    public void AbstractFactory(){
        DocumentBuilderFactory.newInstance();
        TransformerFactory.newInstance();
        XPathFactory.newInstance();
    }
    @Test
    public void FactoryMethod(){
        Calendar calendar=Calendar.getInstance();
        ResourceBundle resourceBundle=ResourceBundle.getBundle("");
        NumberFormat numberFormat=NumberFormat.getInstance();
        Charset charset=Charset.forName("utf-8");

    }

    @Test
    public void Singleton(){
        Runtime runtime=Runtime.getRuntime();
        Desktop desktop=Desktop.getDesktop();
        SecurityManager securityManager=System.getSecurityManager();

    }

    @Test
    public void Visitor(){
        //AnnotationValue
        //AnnotationValueVisitor
       // ElementVisitor
    }
}
