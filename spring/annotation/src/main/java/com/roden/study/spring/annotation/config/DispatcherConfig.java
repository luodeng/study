package com.roden.study.spring.annotation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//相当于<mvc:annotation-driven/>
@Configuration
//包含<bean class="org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping"/>
@EnableWebMvc
//相当于<context:component-scan base-package="com.roden.study.spring.annotation"/>
@ComponentScan(basePackages="com.roden.study.spring.annotation")
public class DispatcherConfig {
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
}
