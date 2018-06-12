package com.market.common.mq;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;

/**
 * 获取消息发送工具
 * @author alex
 */
public class JmsTemplateProvider {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(JmsTemplateProvider.class);
	private static JmsTemplate jms = null;
	
	// 非公平（效率较高）可重入锁
	private static final Lock lock = new ReentrantLock();
	
	private static void load() throws Exception{
		if(jms == null) {
			lock.lock();
			try {
				jms = new JmsTemplate(ActivemqConnectionProvider.getConnectionFactory());
			} finally {
				lock.unlock();
			}
		}
	}
	
	public static JmsTemplate getJmsTemplate() throws Exception{
		if(jms == null){
			lock.lock();
			try {
				load();
			} finally {
				lock.unlock();
			}
		}
		return jms;
	}
	
}
