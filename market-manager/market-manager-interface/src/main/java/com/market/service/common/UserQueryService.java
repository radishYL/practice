package com.market.service.common;

import com.market.common.entity.UserBasicBean;
import com.market.service.callback.DubboCallback;

public interface UserQueryService {

	public UserBasicBean findUser(long uid);
	
	public UserBasicBean findUserEx(long uid);
	
	public void findUserAsync(long uid,DubboCallback callback);
	
}
