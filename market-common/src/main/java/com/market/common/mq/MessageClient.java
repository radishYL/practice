package com.market.common.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 发送消息的客户端
 * @author alex
 */
public class MessageClient {
	
	private static final Logger logger = Logger.getLogger(MessageClient.class);
	
	private MessageClient(){
		
	}
	
	private static class ClientHolder {
		private static final MessageClient client = new MessageClient();
	}
	
	public static MessageClient getInstance(){
		return MessageClient.ClientHolder.client;
	}
	
	/**
	 * 约定--消息发送之前根据msgId进行入库
	 * 		 发送之后更改消息状态
	 * @param message
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public MqResponse sendMsg(MQMessage message){
		MsgSendResponse ret = new MsgSendResponse();
		try {
			JmsTemplate jms = JmsTemplateProvider.getJmsTemplate();
			jms.send(message.getData().getQueueName(),new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(message.getData());
				}
			});
		} catch (Exception e) {
			ret.setCode(MsgSendResponse.Code.MSG_SEND_ERROR.getCode());
			ret.setMessage(MsgSendResponse.Code.MSG_SEND_ERROR.getMsg());
			logger.error("----", e);
		}
		return ret;
	}
	
	
}
