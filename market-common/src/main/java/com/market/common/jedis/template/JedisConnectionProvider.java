package com.market.common.jedis.template;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import com.market.common.utils.PropertiesReader;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis连接工厂
 * @author alex
 *
 */
public class JedisConnectionProvider {
	
	private static Map<Integer,JedisConnectionFactory> queueMap = new ConcurrentHashMap<>();
	
	private static final String CONF_PATH = "prop/redisconf.properties";
	
	/*
	 * 使用jedis操作redis
	 */
	private static JedisPool jedisPool;
	
	private static final Object lock_fac = new Object();
	private static final Object lock_pool = new Object();
	
	private static JedisConnectionFactory jedisConnection = null;
	
	public static JedisConnectionFactory getConnectionFactory(int dbIndex) throws Exception{
		/*
		 * java中方法参数传递
		 * 基本数据类型是值传递 传递的是值的副本 副本的修改 对调用方无影响
		 * 对象数据类型是引用传递 传递的是引用的副本 副本也指向同一内存地址
		 * 副本的修改同样会影响到调用方（如果传递的是null引用的话，引用副本则会指向新的内存地址，不会影响调用方）
		 * String也是如此 不同的是 String引用指向的是常量池 副本修改的时候会指向新的内存地址 同样不会影响调用方
		 */
//		JedisConnectionFactory jedisConnection = null;
		jedisConnection = queueMap.get(dbIndex);
		if(jedisConnection == null){
			synchronized (lock_fac) {
				jedisConnection = queueMap.get(dbIndex);
				if(jedisConnection == null){
					initFactory();
				}
			}
		}
		// 选择数据库
		if(jedisConnection != null){
			jedisConnection.setDatabase(dbIndex);
			/**
			 * 设置完属性之后必须调用此方法 否则会报异常
			 */
			jedisConnection.afterPropertiesSet();
			queueMap.put(dbIndex, jedisConnection);
		}
		return jedisConnection;
	}
	
	public static JedisPool getJedisPool() throws Exception{
		if(jedisPool == null){
			synchronized (lock_pool) {
				if(jedisPool == null){
					initPool();
				}
			}
		}
		return jedisPool;
	}
	
	/**
	 * 初始化连接工厂
	 * @param jedisConnection
	 * @throws Exception
	 */
	private static void initFactory() throws Exception{
		if(jedisConnection == null){
			synchronized (lock_fac) {
				JedisPoolConfig poolConf = new JedisPoolConfig();
				poolConf.setMaxIdle(Integer.parseInt(PropertiesReader.getValue(CONF_PATH, "redis.maxIdle")));
				poolConf.setMaxTotal(Integer.parseInt(PropertiesReader.getValue(CONF_PATH, "redis.maxTotal")));
				poolConf.setMaxWaitMillis(Long.valueOf(PropertiesReader.getValue(CONF_PATH, "redis.maxWaitMillis")));
				poolConf.setTestOnBorrow(Boolean.valueOf(PropertiesReader.getValue(CONF_PATH, "redis.testOnBorrow")));
				poolConf.setBlockWhenExhausted(Boolean.valueOf(PropertiesReader.getValue(CONF_PATH, "redis.blockWhenExhausted")));
				poolConf.setTestWhileIdle(Boolean.valueOf(PropertiesReader.getValue(CONF_PATH, "redis.testWhileIdle")));
				poolConf.setMinEvictableIdleTimeMillis(Long.valueOf(PropertiesReader.getValue(CONF_PATH, "redis.minEvictableIdleTimeMillis")));
				jedisConnection = new JedisConnectionFactory(poolConf);
				jedisConnection.setHostName(PropertiesReader.getValue(CONF_PATH,"redis.hostName"));
				jedisConnection.setPort(Integer.parseInt(PropertiesReader.getValue(CONF_PATH, "redis.port")));
				jedisConnection.setPassword(PropertiesReader.getValue(CONF_PATH,"redis.password"));
				jedisConnection.setTimeout(Integer.parseInt(PropertiesReader.getValue(CONF_PATH,"redis.timeout")));
			}
		}
	}
	
	/**
	 * 初始化Jedis连接池
	 * @throws Exception
	 */
	private static void initPool()throws Exception{
		if(jedisPool == null){
			synchronized (lock_pool) {
				JedisPoolConfig poolConf = new JedisPoolConfig();
				poolConf.setMaxIdle(Integer.parseInt(PropertiesReader.getValue(CONF_PATH, "redis.maxIdle")));
				poolConf.setMaxTotal(Integer.parseInt(PropertiesReader.getValue(CONF_PATH, "redis.maxTotal")));
				poolConf.setMaxWaitMillis(Long.valueOf(PropertiesReader.getValue(CONF_PATH, "redis.maxWaitMillis")));
				poolConf.setTestOnBorrow(Boolean.valueOf(PropertiesReader.getValue(CONF_PATH, "redis.testOnBorrow")));
				poolConf.setBlockWhenExhausted(Boolean.valueOf(PropertiesReader.getValue(CONF_PATH, "redis.blockWhenExhausted")));
				poolConf.setTestWhileIdle(Boolean.valueOf(PropertiesReader.getValue(CONF_PATH, "redis.testWhileIdle")));
				poolConf.setMinEvictableIdleTimeMillis(Long.valueOf(PropertiesReader.getValue(CONF_PATH, "redis.minEvictableIdleTimeMillis")));
				jedisPool = new JedisPool(poolConf,
						PropertiesReader.getValue(CONF_PATH,"redis.hostName"), 
						Integer.parseInt(PropertiesReader.getValue(CONF_PATH, "redis.port")), 
						Integer.parseInt(PropertiesReader.getValue(CONF_PATH,"redis.timeout")),
						PropertiesReader.getValue(CONF_PATH,"redis.password"));
			}
		}
	}
	
}
