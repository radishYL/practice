package com.market.common.mq;

import java.io.Serializable;

public class MessageBody implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long msgId;
	private String queueName;

	public MessageBody(long msgId,String queueName) {
		super();
		this.msgId = msgId;
		this.queueName = queueName;
	}

	public long getMsgId() {
		return msgId;
	}

	public String getQueueName() {
		return queueName;
	}

	
}
