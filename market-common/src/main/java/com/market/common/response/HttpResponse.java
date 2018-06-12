package com.market.common.response;

public class HttpResponse {
	
	public static enum RESULT {
		BLANK_URL("url不能为空");
		
		private String message;
		
		private RESULT(String message){
			this.message = message;
		}
		
		public String getMsg(){
			return this.message;
		}
		
	}
	
}
