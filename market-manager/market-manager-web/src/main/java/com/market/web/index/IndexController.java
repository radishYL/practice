package com.market.web.index;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.rpc.RpcContext;
import com.market.common.entity.UserBasicBean;
import com.market.common.response.MarketResponse;
import com.market.service.callback.DubboCallback;
import com.market.service.common.UserQueryService;
import com.market.service.common.UserService;

/**
 * 主页表现层控制器
 * @author alex
 */
@Controller
// 单例
@Scope("singleton")
// 懒加载
@Lazy(value=true)
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private UserQueryService userQueryService;
	@Autowired
	private DubboCallback dubboCallback;
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String index(HttpServletRequest request,Model model){
		String userId = String.valueOf(request.getSession().getAttribute("userId"));
		if(StringUtils.isNotBlank(userId)){
			model.addAttribute("userId", userId);
		}
		context.publishEvent("来到首页了");
		
		return "index/index";
	}
	
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	public MarketResponse register(HttpServletRequest request,String cellphone, String password, int sex, String name){
		return userService.saveUser(cellphone, password, sex, name);
	}
	
//	@RequestMapping(value="/user/find",method=RequestMethod.POST)
//	@ResponseBody
//	public UserBasicBean find(String userId){
//		UserBasicBean user = userQueryService.findUser(Long.valueOf(userId));
//		System.out.println(user);// 同步结果 为null
//		// 若设置return为false、此处不会创建future对象
//		Future<UserBasicBean> future = RpcContext.getContext().getFuture();
//		try {
//			user = future.get();// 异步获取结果 此处会阻塞等待异步结果
//			System.out.println(user);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return user;
//	}
	
//	@RequestMapping(value="/user/find",method=RequestMethod.POST)
//	public void findSync(String userId){
//		userQueryService.findUserAsync(Long.valueOf(userId), dubboCallback);
//	}
	
	@RequestMapping(value="/user/find",method=RequestMethod.POST)
	@ResponseBody
	public UserBasicBean find(String userId) throws InterruptedException, ExecutionException{
		UserBasicBean user = userService.findUserEx(Long.valueOf(userId));
		Future<UserBasicBean> future = RpcContext.getContext().getFuture();
		System.out.println("--dubbo:--"+future.get());
		return user;
	}
	
	
	
}
