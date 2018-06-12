package com.market.service.message;

import com.market.common.mq.MQMessage;
import com.market.common.mq.MqResponse;

public interface MessageService {

	@SuppressWarnings("rawtypes")
	public MqResponse send(MQMessage message);
	
}
