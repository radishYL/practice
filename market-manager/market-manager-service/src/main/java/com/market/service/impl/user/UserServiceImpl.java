package com.market.service.impl.user;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.market.common.entity.UserBasicBean;
import com.market.common.mapper.ex.UserBasicBeanMapperEx;
import com.market.common.mq.MQMessage;
import com.market.common.response.MarketResponse;
import com.market.common.utils.IDGennerator;
import com.market.service.common.UserService;
import com.market.service.message.MessageService;
import com.market.service.msg.body.UserRegisterBody;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserBasicBeanMapperEx userBasicBeanMapperEx;
	@Autowired
	private ApplicationContext context;
	@Resource
	private UserService userService;
	@Resource
	private MessageService messageService;
	
	@Override
	@Transactional
	public MarketResponse saveUser(String cellphone, String password, int sex, String name) {
		MarketResponse res = new MarketResponse();
		try {
			UserBasicBean user = new UserBasicBean();
			long id = IDGennerator.getInstance().genId();
			user.setId(id);
			user.setName(name);
			user.setPassword(password);
			user.setSex(sex);
			user.setCellphone(Integer.valueOf(cellphone));
			context.publishEvent(user);
			userService.saveUserEx(user);
			UserRegisterBody data = new UserRegisterBody(IDGennerator.getInstance().genId(), "user-register");
			data.setUserId(user.getId());
			MQMessage<UserRegisterBody> msg = new MQMessage<UserRegisterBody>(data);
			messageService.send(msg);
		} catch (Exception e) {
			res.setCode(1);
			res.setMessage("--用户注册失败--");
			throw new RuntimeException(e);
		}
		return res;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void saveUserEx(UserBasicBean user){
		userBasicBeanMapperEx.insertSelective(user);
	}

	@Override
	public UserBasicBean findUserEx(long uid) {
		UserBasicBean user = userBasicBeanMapperEx.selectByPrimaryKey(uid);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return user;
	}
	
}
