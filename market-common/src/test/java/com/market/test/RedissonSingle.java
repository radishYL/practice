package com.market.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RAtomicDouble;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

public class RedissonSingle {
	
	private static final Object lock = new Object();
	
	private static Map<Integer,RedissonClient> queueMap = new ConcurrentHashMap<>();
	
	public static RedissonClient getInstance(int dbIndex){
		RedissonClient client = queueMap.get(dbIndex);
		if(client == null){
			synchronized (lock) {
				client = queueMap.get(dbIndex);
				if(client == null){
					client = init(dbIndex);
					queueMap.put(dbIndex, client);
				}
			}
		}
		return client;
	}
	
	private static RedissonClient init(int dbIndex){
		Config conf = new Config();
		//设置编码
		conf.setCodec(new StringCodec());
		conf.setRedissonReferenceEnabled(true);
		SingleServerConfig server = conf.useSingleServer();
		server.setAddress("127.0.0.1:6379");
		server.setPassword("123456");
		server.setConnectionPoolSize(100);
		// 设置连接的超时时间
		server.setConnectTimeout(30000);
		server.setConnectionMinimumIdleSize(10);
		server.setIdleConnectionTimeout(10000);
		// 等待节点回复的超时时间
		server.setTimeout(30000);
		//当与某个节点的连接断开时，等待与其重新建立连接的时间间隔
		server.setReconnectionTimeout(10000);
		// 设置连接数据库 0-15
		server.setDatabase(dbIndex);
		// 创建连接客户端
		return Redisson.create(conf);
	}
	
	public static void main(String[] args) {
		RedissonClient client = getInstance(1);
//		RBucket<Object> bucket = client.getBucket("name");
//		System.out.println(bucket.get());
		RList<Object> names = client.getList("cities");
		names.addAsync("beijing");
		names.addAsync("wuhan");
//		client.shutdown();
		System.out.println("end");
	}
	
	@Test
	public void test(){
		RedissonClient client = getInstance(1);
		RList<Object> list = client.getList("names");
//		RAtomicDouble atomicDouble = client.getAtomicDouble("money");
//		double addAndGet = atomicDouble.addAndGet(-20.33);
//		atomicDouble.compareAndSet(expect, update);
		list.add("jack");
		System.out.println(list);
	}
	
	public void shutdown(){
		if(queueMap.containsValue(this)){
			queueMap.forEach((K,V)->{
				if(this.equals(V)){
					queueMap.remove(K);
				}
			});
		}
	}
	
	@Test
	public void test02(){
		RedissonClient client = getInstance(1);
		RLock rlock = client.getLock("lock_names");
		boolean tryLock = rlock.tryLock();
		System.out.println(tryLock);
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			rlock.unlock();
			System.out.println("lock 释放");
		}
		
	}
	
	@Test
	public void test03() throws InterruptedException{
		RedissonClient client = getInstance(1);
		RLock rlock = client.getLock("lock_names");
		boolean locked = rlock.isLocked();
		System.out.println(locked);
		if(locked){
			System.out.println("解锁");
			// 非当前线程获取的锁 故当前线程不能释放锁
			// 否则会抛异常
			rlock.unlock();
		}
	}
	
	@Test
	public void test04(){
		BigDecimal money = new BigDecimal("200").setScale(2, RoundingMode.FLOOR);
		System.out.println(money.multiply(new BigDecimal("-1")));
	}
}
