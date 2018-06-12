package com.market.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/application-*.xml")
public class SpringEventTest {
	
	@Autowired
	ApplicationContext context;
	
	@Test
	public void test1(){
		
		/*
		 * Spring 事件驱动编程
		 * 1.容器事件 用于框架
		 * 2.对象事件 用于业务
		 */
		context.publishEvent("event");
		
	}
	
	@Test
	public void test2(){
		
		
	}
	
	@EventListener
	public void listen(String event){
		
		System.err.println(event);
		
	}
	
}
