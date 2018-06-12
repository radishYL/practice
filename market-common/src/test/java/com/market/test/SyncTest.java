package com.market.test;

import org.junit.Test;

public class SyncTest {
	
//	private static final Object lock = new Object();
	
	@Test
	public void test(){
		User user = null;
//		if(value == null){
//			synchronized (lock) {
//				if(value == null){
//					load(value);
//				}
//			}
//		}
		load(user);
		System.out.println(user);
	}
	
	void load(User user){
		if(user == null){
			user = new User();
		}
	}
	
		public static void changeValue(int value){
			value = 0;
		}

		public static void main(String[] args) {
			int value = 2010;
			changeValue(value);
			System.out.println(value);
		}
}
