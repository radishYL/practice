package com.market.service.msg.body;

import com.market.common.mq.MessageBody;

public class UserRegisterBody extends MessageBody{

	private static final long serialVersionUID = 1L;

	public UserRegisterBody(long msgId,String queueName){
		super(msgId, queueName);
	}
	
	private long userId;
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
