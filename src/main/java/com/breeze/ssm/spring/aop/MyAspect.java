package com.breeze.ssm.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 切面
 */
@Aspect
@Component
public class MyAspect {

 	/**
	 * 切点
	 */
	@Pointcut("execution(* com.breeze.ssm.spring.aop.*.*(..))")
	public void pointCut() {}
	
	/**
	 * 前置通知：通常可以做一些系统初始化的工作
	 */
	@Before("pointCut()")
	public void before() {
		System.out.println("I'm before");
	}

	/**
	 * 后置通知
	 */
	@After("pointCut()")
	public void After() {
		System.out.println("I'm after");
	}
	
	/**
	 * 返回后通知
	 */
	@AfterReturning("pointCut()")
	public void afterReturn() {
		System.out.println("I'm afterReturn");
	}
	
	/**
	 * 抛出异常通知
	 */
	@AfterThrowing("pointCut()")
	public void afterThrow() {
		System.out.println("I'm afterThrow");
	}
	
	/**
	 * 环绕通知 ：可以初始化资源、安全检查等工作
	 * @throws Throwable 
	 */
	@Around("pointCut()")
	public Object Around(ProceedingJoinPoint pjp) throws Throwable {
		Object object = null;		
		System.out.println("I'm around1");
		object = pjp.proceed();
		System.out.println("I'm around2");
		return object;
	}
	
}
