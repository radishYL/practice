package com.market.service.common;

import com.market.common.entity.UserBasicBean;
import com.market.common.response.MarketResponse;

public interface UserService {

	public MarketResponse saveUser(String cellphone,String password,int sex,String name);
	
	public void saveUserEx(UserBasicBean user);
	
	public UserBasicBean findUserEx(long uid);
}
