package com.market.common.spring.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextUtils implements ApplicationContextAware {
	
	private ApplicationContext context;
	
	public ApplicationContext getContext(){
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
}
