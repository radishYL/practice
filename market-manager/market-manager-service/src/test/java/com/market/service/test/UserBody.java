package com.market.service.test;

import com.market.common.mq.MessageBody;

public class UserBody extends MessageBody{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserBody(long msgId, String queueName) {
		super(msgId, queueName);
	}

	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
