package com.market.common.jedis.template;import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Redis工具类 默认是0库
 * @author alex
 */
public class JedisTemplate implements JedisMethod{

	private static JedisClient client;
	
	private static Map<Integer, JedisClient> queueMap = new ConcurrentHashMap<>();
	
	private static final Object lock = new Object();
	
	private JedisTemplate(){
	}
	
	public static JedisTemplate getIntance(int dbIndex){
		client = queueMap.get(dbIndex);
		if(client == null){
			synchronized (lock) {
				client = queueMap.get(dbIndex);
				if(client == null){
					client = JedisClient.getJedisClient(dbIndex);
					queueMap.put(dbIndex,client);
				}
			}
		}
		return new JedisTemplate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void set(String key, Object value) {
		client.opsForValue().set(key, value);
	}

	@Override
	public Object get(String key) {
		Object value = client.opsForValue().get(key);
		return value;
	}
	
}
