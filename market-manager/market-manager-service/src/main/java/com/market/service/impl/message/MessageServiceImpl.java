package com.market.service.impl.message;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.market.common.mq.DefaultMessageRecieve;
import com.market.common.mq.MQMessage;
import com.market.common.mq.MessageClient;
import com.market.common.mq.MessageHanlder;
import com.market.common.mq.MessageListenner;
import com.market.common.mq.MessageRecieve;
import com.market.common.mq.MqResponse;
import com.market.service.message.MessageService;

/**
 * 消息发送服务
 * @author alex
 *
 */
@Service("MessageService")
public class MessageServiceImpl implements MessageService{
	
	@Resource
	private MessageHanlder userRegisterHandler;
	
	@PostConstruct
	public void init() throws Exception{
		/*
		 * 用户注册
		 */
		MessageListenner userRegisterListenner = new MessageListenner(userRegisterHandler);
		MessageRecieve userRegisterRecieve = new DefaultMessageRecieve("user-register", userRegisterListenner);
		userRegisterRecieve.start();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public MqResponse send(MQMessage message) {
		MqResponse res = MessageClient.getInstance().sendMsg(message);
		return res;
	}

}
