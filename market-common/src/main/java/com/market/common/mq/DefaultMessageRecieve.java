package com.market.common.mq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class DefaultMessageRecieve implements MessageRecieve {
	
	private static Logger log = Logger.getLogger(DefaultMessageRecieve.class);
	
	private String queueName;
	
	private MessageListenner msgListenner;
	
	public DefaultMessageRecieve(String queueName,MessageListenner msgListenner){
		this.queueName = queueName;
		this.msgListenner = msgListenner;
	}
	
	private volatile boolean runFlag = true;
	
	@Override
	public void start() {
		if(runFlag){
			if(StringUtils.isBlank(queueName)){
				throw new NullPointerException("queueNme can not be null or empty");
			}
			if(msgListenner == null){
				throw new NullPointerException("msgListenner can not be null");
			}
			hand();
		}
	}

	@Override
	public void stop() {
		runFlag = false;
	}
	
	private void hand(){
		if(runFlag){
			Thread executor = new Thread(new Runnable() {
				@Override
				public void run() {
					Connection conn = null;
					Session session = null;
					try {
						conn = ActivemqConnectionProvider.getConnection();
						// 不开启事务 自动确认消息机制
						session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
						Queue queue = session.createQueue(queueName);
						MessageConsumer consumer = session.createConsumer(queue);
						consumer.setMessageListener(msgListenner);
					} catch (Exception e) {
						log.error("queue:"+queueName+" start error", e);
						if(conn != null){
							try {
								conn.stop();
								if(session != null){
									session.close();
								}
							} catch (JMSException e1) {
								log.error("--queue:"+queueName+" conn close error--", e1);
							}
						}
					}
				}
			},"activemq_recieve_"+queueName);
			executor.start();
		}
	}
}
