package com.breeze.ssm.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserTest {
  
	public static void main(String[] args) {
	
		@SuppressWarnings("resource")
		ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
		User user = (User) application.getBean("user");
		System.out.println(user.getName());
		System.out.println(user.getAge());
	}
}
