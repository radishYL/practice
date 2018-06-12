package com.market.service.test;

import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.springframework.jms.core.JmsTemplate;

import com.market.common.mq.JmsTemplateProvider;
import com.market.common.mq.MQMessage;
import com.market.common.mq.MessageBody;
import com.market.common.mq.QueueContainner;
import com.market.service.impl.message.MessageServiceImpl;

public class MyTest {

	public static void main(String[] args) throws Exception {
		
		MessageServiceImpl service = new MessageServiceImpl();
		
//		service.init();
		
		MessageBody data = new MessageBody(001L, QueueContainner.USER_REGISTER_QUEUE);
		
		MQMessage<MessageBody> msg = new MQMessage<MessageBody>(data);
		
		service.send(msg);
		
		JmsTemplate jmsTemplate = JmsTemplateProvider.getJmsTemplate();
		Message receive = jmsTemplate.receive(QueueContainner.USER_REGISTER_QUEUE);
		ObjectMessage omsg = (ObjectMessage)receive;
		MessageBody body = (MessageBody)(omsg.getObject());
		System.err.println(body.getQueueName());
		
		Thread.sleep(10000);
		
	}
	
	
}
