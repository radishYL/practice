package com.market.service.callback;

import java.io.Serializable;

import com.market.common.entity.UserBasicBean;
/**
 * Duboo异步调用回调
 * @author alex
 *
 */
public interface DubboCallback extends Serializable{

	public void onReturn(UserBasicBean user);
	
	public void onThrow(long userId,Throwable t);
	
}
