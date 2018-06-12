package com.market.service.spring.tool;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
@Service
public class ToolService implements ApplicationContextAware,InitializingBean{

	@Override
	public void afterPropertiesSet() throws Exception {
		System.err.println("ToolService 初始化");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.err.println("ToolService 加载spring上下文对象");
	}

}
