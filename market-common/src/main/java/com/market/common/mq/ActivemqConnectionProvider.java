package com.market.common.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.connection.CachingConnectionFactory;

public class ActivemqConnectionProvider {

//	private static final String COMF_PATH = "classpath:prop/activemq.properties";
	private static final int SESSION_CACHE_SIZE = 100;
	private static Connection conn;
	private static ConnectionFactory connectionFactory;
	
	
	public static ConnectionFactory getConnectionFactory() throws Exception{
		return connectionFactory;
	}
	
	public static Connection getConnection() throws JMSException{
		if(conn == null){
			/*
			 * amq连接工厂
			 * 真正产生amq连接的连接工厂
			 * 有JMS服务器生产商提供
			 */
			ActiveMQConnectionFactory amqCon = new ActiveMQConnectionFactory();
			amqCon.setBrokerURL("tcp://127.0.0.1:61616");
			amqCon.setUserName("admin");
			amqCon.setPassword("admin");
			/*
			 * Spring用于管理真正的ConnectionFactory的ConnectionFactory
			 */
			CachingConnectionFactory cacheCon = new CachingConnectionFactory(amqCon);
			cacheCon.setSessionCacheSize(SESSION_CACHE_SIZE);
			/*
			 * spring用来发送消息的生产者
			 */
//			PooledConnectionFactory pool = new PooledConnectionFactory(amqCon);
//			pool.setIdleTimeout(100000);
//			pool.setCreateConnectionOnStartup(true);
//			pool.setExpiryTimeout(10000);
//			pool.setMaxConnections(100);
			connectionFactory = cacheCon;
			conn = connectionFactory.createConnection();
			conn.start();
		}
		return conn;
	}
	
	public static void stop() throws JMSException{
		if(conn != null){
			conn.stop();
		}
	}
}
