package com.breeze.ssm.spring.ioc;

import org.springframework.stereotype.Component;

@Component
public class Student {

	private String sName;
	
	private int sNum;//学号

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public int getsNum() {
		return sNum;
	}

	public void setsNum(int sNum) {
		this.sNum = sNum;
	}
	
	
}
