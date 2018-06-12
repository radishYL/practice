package com.market.service.impl.user;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.market.common.entity.UserBasicBean;
import com.market.common.mapper.ex.UserBasicBeanMapperEx;
import com.market.service.callback.DubboCallback;
import com.market.service.common.UserQueryService;
import com.market.service.common.UserService;

@Service("userQueryService")
public class UserQueryServiceImpl implements UserQueryService {
	
	@Resource
	private UserBasicBeanMapperEx userBasicBeanMapperEx;
	@Resource
	private UserQueryService userQueryService;
	@Resource
	private UserService userService;
	
	
	@Override
	public UserBasicBean findUser(long uid) {
		UserBasicBean user = userQueryService.findUserEx(uid);
		System.err.println(user);
		return user;
	}

	@Override
	public UserBasicBean findUserEx(long uid) {
		UserBasicBean user = userService.findUserEx(uid);
		return user;
	}

	@Override
	public void findUserAsync(long uid, DubboCallback callback) {
		try {
			UserBasicBean user = userBasicBeanMapperEx.selectByPrimaryKey(uid);
//			callback.onReturn(user);
			System.err.println(user);
		} catch (Exception e) {
//			callback.onThrow(uid, e);
		}
	}

	
	
	
}
