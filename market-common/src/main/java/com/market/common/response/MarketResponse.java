package com.market.common.response;

import java.io.Serializable;

public class MarketResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private int code = 0;
	
	private String message = "success";

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
	
}
