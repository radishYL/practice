package com.market.web.nitify;

import com.market.common.entity.UserBasicBean;

public class DubboNotify {
	
	/**
	 * 注意 此处参数只能与dubbo服务返回值一致
	 * @param user
	 */
	public void onReturn(UserBasicBean user){
		
		System.err.println("--dubbo 异步回调--");
		
		System.err.println(user.getId());
		
	}
	
	/**
	 * 注意 此处的参数只能是Throwable
	 * @param t
	 */
	public void onThrow(Throwable t){
		System.err.println("--dubbo 异步异常回调--");
		System.err.println(t);
	}
	
}
