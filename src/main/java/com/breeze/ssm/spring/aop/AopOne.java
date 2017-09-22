package com.breeze.ssm.spring.aop;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.InvocationHandler;

/*
 * 在学习AOP之前我们先了解一下OOP
 * OOP概念及作用：
 * OOP主要是为了实现编程的重用性、灵活性和扩展性。它的几个特征分别是封装、继承、多态、抽象、OOP重点体现在编程架构，强调的是类之间的层次关系。
 * (例如：Cat、Dog都有run()方法，那么我们就可以将抽象出一个带有run()的Animal父类让它们继承)
 * 
 * AOP概念：
 * AOP为Aspect Oriented Programming的缩写，即面向切面编程（也叫面向方面），
 * 是一种可以通过预编译方式和运行期动态代理实现在不修改源代码的情况下给程序动态统一添加功能的一种技术。
 * 这种在运行时，动态地将代码切入到类的指定方法、指定位置上的编程思想就是面向切面的编程。
 * 
 * 切面：一般而言，我们管切入到指定类指定方法的代码片段称为切面。（切面，即存放那些抽取出来的重复代码的类）
 * 
 * 切入点：而切入到哪些类、哪些方法则叫切入点。(也就是被切入的"地方"(类或者方法))
 * 
 * 通知：就是切入到类或者方法后要执行的代码(即抽取出来的那部分重复代码)
 * 
 * 有了AOP，我们就可以把几个类共有的代码，抽取到一个切面中，等到需要时再切入对象中去，从而改变其原有的行为。
 * 
 * 为什么使用AOP而不是将各类中重复的部分抽取到一个独立类的方法中，然后再调用该方法呢？
 * 原因就是如果这样的话，该独立类就与其他类耦合了，只要独立类改变就会影响到其他的类。而使用AOP则不会。
 * 疑问？虽然切面没有和其他类进行耦合，如果切面改变的话，那那些类的行为不也是会被改变嘛？
 * 
 * AOP的实现原理：动态代理模式
 *  
 * 
 */
public class AopOne implements InvocationHandler {

	public Object object;
	
	public AopOne(Object object) {
		this.object = object;
	}
	
	public Object invoke(Object object, Method method, Object[] args) throws Throwable {
		
		method.invoke(object, args);
		return null;
	}
	
}
