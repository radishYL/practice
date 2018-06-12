package com.market.test;

public enum Singleton{

	INSTANCE;
	
	public void method(int x){
		System.out.println(x);
	}
	
	public int getMod(int x){
		return x % 2;
	}
}
