package com.market.common.mq;

import java.io.Serializable;

public class MsgSendResponse implements Serializable,MqResponse{

	private static final long serialVersionUID = 1L;

	private int code = 0;
	
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static enum Code {
		MSG_SEND_ERROR(1,"消息发送异常");
		private int code;
		private String msg;
		private Code(int code,String msg){
			this.code = code;
			this.msg = msg;
		}
		public int getCode() {
			return code;
		}
		public String getMsg() {
			return msg;
		}
	}
}
