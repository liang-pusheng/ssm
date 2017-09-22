package com.breeze.ssm.spring.ioc;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserTest {
  
	public static void main(String[] args) {
	
		@SuppressWarnings("resource")
		ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
		User user = (User) application.getBean("user");
		System.out.println(user);
		System.out.println("用户名:" + user.getName());
		System.out.println("用户年龄:" + user.getAge());
		System.out.println("学生的姓名:" + user.getStudent().getsName() + ":::" + "学生的学号:" + user.getStudent().getsNum());
		ArrayList<String> list = user.getList();
		System.out.println(list);
		HashMap<Integer, String> map = user.getMap();
		System.out.println(map);
		User user2 = (User)application.getBean("user");
		System.out.println(user2);
	}
}
