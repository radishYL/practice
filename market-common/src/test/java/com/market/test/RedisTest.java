package com.market.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.market.common.jedis.client.JedisClient;
import com.market.common.jedis.template.JedisConnectionProvider;
import com.market.common.jedis.template.JedisTemplate;
import com.market.common.mq.MQMessage;
import com.market.common.mq.MessageClient;
import com.market.common.mq.MqResponse;
import com.market.common.utils.PropertiesReader;

import redis.clients.jedis.Jedis;

public class RedisTest {

	@Test
	public void test01() throws IOException{
		
		String CONF_PATH = "prop/redisconf.properties";
		String value = PropertiesReader.getValue(CONF_PATH,"redis.hostName");
		System.out.println(value);
		
	}
	
	public static void main1(String[] args) throws Exception {
		
		Jedis jedis = JedisClient.getJedis(1);
		System.out.println(jedis.get("name"));
	}
	
	@Test
	public void test02(){
		JedisTemplate intance = JedisTemplate.getIntance(1);
		System.out.println(intance.get("name"));
	}
	
	@Test
	public void test04() throws Exception{
		RedisTemplate<String,Object> client = new RedisTemplate<>();
		client.setConnectionFactory(JedisConnectionProvider.getConnectionFactory(1));
		/*
		 * 如果不配置Serializer
		 * 存储的时候就只能存储String
		 */
		client.setKeySerializer(new StringRedisSerializer());
		client.setValueSerializer(new StringRedisSerializer());
		// 开启事务
		client.setEnableTransactionSupport(true);
		client.afterPropertiesSet();
		Object value = client.opsForValue().get("name");
		System.err.println(value);
	}
	
	@Test
	public void test05(){
		TestMsg body = new TestMsg(1L, "test");
		MQMessage<TestMsg> msg = new MQMessage<TestMsg>(body);
		MqResponse sendMsg = MessageClient.getInstance().sendMsg(msg);
		System.out.println(sendMsg);
	}
	
	@Test
	public void test06() throws FileNotFoundException, IOException, ClassNotFoundException{
		Singleton s1 = Singleton.INSTANCE;
		Singleton s2 = Singleton.INSTANCE;
		
		System.out.println(s1 == s2);
		
		FileOutputStream bos = new FileOutputStream("/Users/alex/Public/tmp.txt");
		
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		
		FileInputStream bis = new FileInputStream("/Users/alex/Public/tmp.txt");
		
		ObjectInputStream ois = new ObjectInputStream(bis);
		
		oos.writeObject(s2);
		oos.close();
		Object s3 = ois.readObject();
		ois.close();
		System.err.println(s2 == s3);
	}
	
	@Test
	public void test07() throws IOException, ClassNotFoundException{
		TestMsg t1 = new TestMsg(111L, "test");
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		
		oos.writeObject(t1);
		
		Object t2 = ois.readObject();
		
		System.out.println(t1 == t2);
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		User u1 = new User();
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		
		oos.writeObject(u1);
		bos.close();
		oos.close();
		Object t2 = ois.readObject();
		
		System.out.println(u1 == t2);
	}
	
	@Test
	public void test08() throws Exception{
		
		Jedis jedis = JedisClient.getJedis(2);
		
		String set = jedis.configGet("databases").get(1);
		
		System.out.println(set);
		
	}
	
	@Test
	public void test09() throws Exception{
		
		Jedis jedis = JedisClient.getJedis(2);
		
		String lua = "local key = KEYS[1] "
				+ "local oldValue = ARGV[1] "
				+ "local newValue = ARGV[2] "
				+ "if redis.call('EXISTS',key) ~= 1 then "
				+ "return redis.call('set',key,newValue) "
				+ "else "
				+ "local value = redis.call('get',key) "
				+ "if value == oldValue then "
				+ "return redis.call('set',key,newValue) "
				+ "else "
				+ "return 0 "
				+ "end "
				+ "end";
		
		Object eval = jedis.eval(lua, 1, "miss","chou","fee");
		
		System.out.println(String.valueOf(eval));
		
	}
	
}
