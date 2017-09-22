package com.breeze.ssm.spring.aop;

import org.springframework.stereotype.Component;

/**
 * 目标类
 */
@Component
public class Target {

	public void say() {
		System.out.println("just say...");
	}
}
