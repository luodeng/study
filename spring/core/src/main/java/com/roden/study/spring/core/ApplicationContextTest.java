package com.roden.study.spring.core;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 1. 获取上下文
 *     - ClassPathXmlApplicationContext：从classpath的XML配置文件中读取上下文并生成上下文定义。应用程序上下文从程序环境变量中取得。
 *     ApplicationContext context = new ClassPathXmlApplicationContext(“application.xml”);
 *
 *     - FileSystemXmlApplicationContext ：由文件系统中的XML配置文件读取上下文。
 *     ApplicationContext context = new FileSystemXmlApplicationContext(“application.xml”); *
 *
 *     - AnnotationConfigApplicationContext
 *
 *     - XmlWebApplicationContext：由Web应用的XML文件读取上下文。
 */
public class ApplicationContextTest {
    @Test
    public void classPathXmlApplicationContext() {
        // 用我们的配置文件来启动一个 ApplicationContext
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        // 从 context 中取出我们的 Bean，而不是用 new MessageServiceImpl() 这种方式
        MessageService messageService = context.getBean(MessageService.class);
        // 这句将输出: hello world
        System.out.println(messageService.getMessage());
    }

    @Test
    public void annotationConfigApplicationContext() {
        // 用我们的配置文件来启动一个 ApplicationContext
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        // 从 context 中取出我们的 Bean，而不是用 new MessageServiceImpl() 这种方式
        MessageService messageService = context.getBean(MessageService.class);
        // 这句将输出: hello world
        System.out.println(messageService.getMessage());
    }
}
