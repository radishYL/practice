package com.market.common.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取properties文件
 * @author alex
 *
 */
public class PropertiesReader {
	
	private static final Object lock = new Object();
	
	private static Properties prop = null;
	
	static {
		if(prop == null){
			synchronized(lock) {
				if(prop == null){
					prop = new Properties();
				}
			}
		}
	}
	
	public static String getValue(String filePath,String key) throws IOException{
		
		prop.load(ClassLoader.getSystemResourceAsStream(filePath));
		
		return prop.getProperty(key);
	}
	
	
}
