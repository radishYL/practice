package com.market.test;

import com.market.common.mq.MessageBody;

public class TestMsg extends MessageBody{

	private static final long serialVersionUID = 1L;

	public TestMsg(long msgId, String queueName) {
		super(msgId, queueName);
	}

	
	
}
