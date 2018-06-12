package com.market.common.constants;

/**
 * http常量
 * @author alex
 *
 */
public class HttpConstant {
	
	public static enum ContentType {
		JSON("application/json"),
		OCTET_STREAM("application/octet-stream"),
		ZIP("application/zip")
		;
		
		private  String value;
		
		ContentType(String value) {
			this.value = value;
		}
		
		public String get(){
			return this.value;
		}
		
	}
	
	
	
}
