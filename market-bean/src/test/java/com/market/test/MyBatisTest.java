package com.market.test;

import java.io.IOException;

import org.apache.ibatis.executor.result.DefaultResultHandler;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisTest {

	public static void main(String[] args) throws IOException {
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("/mybatis/SqlMapConfig.xml")); 
		SqlSession session = factory.openSession();
		session.select("select * from user", new DefaultResultHandler());
		Object selectOne = session.selectOne("", 1);
		session.commit();
	}
	
}
