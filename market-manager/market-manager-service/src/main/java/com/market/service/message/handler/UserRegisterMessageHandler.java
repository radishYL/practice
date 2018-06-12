package com.market.service.message.handler;

import org.springframework.stereotype.Component;

import com.market.common.mq.MessageBody;
import com.market.common.mq.MessageHanlder;
import com.market.service.msg.body.UserRegisterBody;

@Component("userRegisterHandler")
public class UserRegisterMessageHandler implements MessageHanlder {

	@Override
	public void hand(MessageBody body) {
		UserRegisterBody msg = (UserRegisterBody)body;
		System.err.println("queue:"+msg.getQueueName()+" deal msg "+msg.getUserId());
	}

}
