package com.market.common.entity.ex;

import com.market.common.entity.UserBasicBean;

public class UserBasicBeanEx extends UserBasicBean{

	
	private static final long serialVersionUID = 1L;

	public static enum sex {
		
		WOMAN(0,"女"),MAN(1,"男"),UNKNOW(2,"未知");
		
		private int sexCode;
		
		private String sexDes;
		
		sex(int sexCode,String sexdes){
			this.sexCode = sexCode;
			this.sexDes = sexdes;
		}
		
		public int get(){
			return this.sexCode;
		}
		public String getDes(){
			return this.sexDes;
		}
	}
	
	public static enum status {
		JUST_REGISTER(0,"仅注册"),BUY(1,"已购买商品"),DEL(10,"注销");
		
		private int status;
		
		private String des;
		
		status(int status,String des){
			this.status = status;
			this.des = des;
		}
		
		public int get(){
			return this.status;
		}
		public String getDes(){
			return this.des;
		}
		
	}
	
}
