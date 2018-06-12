package com.market.common.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class MessageListenner implements MessageListener {
	
	private MessageHanlder handler;
	
	public MessageListenner(MessageHanlder handler) {
		this.handler = handler;
	}
	
	@Override
	public void onMessage(Message message) {
		try {
			ObjectMessage objMsg = (ObjectMessage)(message);
			/**
			 * 修改消息状态 以及处理结果
			 */
			MessageBody msgBody = (MessageBody)objMsg.getObject();
			handler.hand(msgBody);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
}
