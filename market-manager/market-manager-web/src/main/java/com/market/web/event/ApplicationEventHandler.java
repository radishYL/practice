package com.market.web.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.market.common.entity.UserBasicBean;

@Component("applicationEventHandler")
public class ApplicationEventHandler {
	
	
	
	/**
	 * 注意:
	 * 		若未使用事件通道
	 * 		此处抛异常将会影响业务
	 * 		不算真正解耦
	 * 		@Async标识异步处理
	 * @throws InterruptedException 
	 */
	@EventListener
	@Async
	public void listen(UserBasicBean user) throws InterruptedException{
		Thread.sleep(100000);
		
		System.out.println(user);
		
		throw new RuntimeException();
		
	}
	
	@EventListener
	@Async
	public void listen(String msg) throws InterruptedException{
		
		Thread.sleep(10000);
		
		System.err.println(msg);
		
	}
	
}
