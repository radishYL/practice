package com.market.common.jedis.template;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 连接redis的客户端
 * @author alex
 */
public class JedisClient extends RedisTemplate<String,Object>{
	
	private JedisClient(){
		
	}
	
	private static final class JedisHolder {
		private static JedisClient client =  null;
		public static JedisClient getClinet(int dbIndex) throws Exception{
			if(client == null){
				client = new JedisClient();
				client.setConnectionFactory(JedisConnectionProvider.getConnectionFactory(dbIndex));
				/*
				 * 如果不配置Serializer
				 * 存储的时候就只能存储String
				 */
				client.setKeySerializer(new StringRedisSerializer());
				client.setValueSerializer(new StringRedisSerializer());
				// 开启事务
				client.setEnableTransactionSupport(true);
				/*
				 * 设置完属性之后 必须调用次此方法 否则会报异常
				 */
				client.afterPropertiesSet();
			}
			return client;
		}
	}
	
	public static JedisClient getJedisClient(int dbIndex){
		try {
			return JedisHolder.getClinet(dbIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
