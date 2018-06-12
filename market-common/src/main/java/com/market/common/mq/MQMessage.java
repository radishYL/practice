package com.market.common.mq;

import java.io.Serializable;

public class MQMessage<T extends MessageBody> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	// 消息体
	private T data;
	
	public MQMessage(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
