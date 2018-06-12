package com.market.common.mq;

import javax.jms.ConnectionFactory;

import org.springframework.jms.listener.DefaultMessageListenerContainer;
/**
 * 消息监听容器
 * @author alex
 *
 */
public class MessageListennerContainer extends DefaultMessageListenerContainer{

	private MessageListenner listenner;
	private String queueName;
	private ConnectionFactory conn;
	
	public MessageListennerContainer(MessageListenner listenner,String queueName,ConnectionFactory conn){
		this.listenner = listenner;
		this.queueName = queueName;
		this.conn = conn;
		super.setDestinationName(queueName);
		super.setMessageListener(listenner);
		super.setConnectionFactory(conn);
	}
	
	public void listen(){
		this.setMessageListener(listenner);
		this.setDestinationName(queueName);
		this.setConnectionFactory(conn);
	}
}
