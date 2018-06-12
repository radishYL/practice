package com.market.common.jedis.template;

public interface JedisMethod {

	public void set(String key,Object value);
	
	public Object get(String key);
	
}
