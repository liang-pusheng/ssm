package com.breeze.ssm.spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
		Target target = (Target) application.getBean("target");
		target.say();
	}
}
