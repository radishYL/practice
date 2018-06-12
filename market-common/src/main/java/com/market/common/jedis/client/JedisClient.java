package com.market.common.jedis.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.market.common.jedis.template.JedisConnectionProvider;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Jedis操作数据库的客户端
 * @author alex
 *
 */
public class JedisClient extends Jedis{

	private static Map<Integer,Jedis> queueMap = new ConcurrentHashMap<>();
	
	private static final Object lock = new Object();
	
	public static Jedis getJedis(int dbIndex) throws Exception{
		Jedis jedis = queueMap.get(dbIndex);
		if(jedis == null){
			synchronized (lock) {
				jedis = queueMap.get(dbIndex);
				if(jedis == null){
					JedisPool jedisPool = JedisConnectionProvider.getJedisPool();
					jedis = jedisPool.getResource();
					jedis.select(dbIndex);
					queueMap.put(dbIndex, jedis);
				}
			}
		}
		return jedis;
	}
	
}
