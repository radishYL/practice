package com.market.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * ID生成器
 * 最多18位
 * @author alex
 */
public class IDGennerator {
	
	private static final Logger log = Logger.getLogger(IDGennerator.class);
	
	private static Map<String,AtomicLong> keyCache = new HashMap<>();
	// 非公平锁 效率较高
	private static Lock lock = new ReentrantLock();
	
	private static DateFormat SDF = new SimpleDateFormat("yyMMddHHmmss");
	
	private static int INC_LEN = 6;
	
	/**
	 * 单例
	 */
	private IDGennerator(){
		
	}
	
	private static class IDGenneratorHolder {
		private static IDGennerator id = new IDGennerator();
	}
	
	public static IDGennerator getInstance(){
		return IDGennerator.IDGenneratorHolder.id;
	}
	
	/**
	 * 年月日时分秒(12位)+自增整形数字(6位)
	 * 每秒最多支持生成999999个ID
	 */
	public long genId(){
		log.info("--开始生成主键--");
		String timeStamp = null;
		long inc = 0L;
		try {
			lock.lock();
			timeStamp = SDF.format(new Date());
			AtomicLong value = keyCache.get(timeStamp);
			if(value == null){
				long initValue = 0L;
				keyCache.put(timeStamp,new AtomicLong(initValue));
				inc = initValue;
			}else {
				inc = keyCache.get(timeStamp).addAndGet(1L);
			}
		}finally {
			lock.unlock();
		}
		String idStr = timeStamp+StringUtils.leftPad(String.valueOf(inc),INC_LEN,"0");
		log.info("--主键生成成功:"+idStr+"--");
		return Long.valueOf(idStr);
	}
	
	
}
