package com.market.service.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newFixedThreadPool(1);
		
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.err.println("call begin");
				Thread.sleep(3000);
				System.err.println("call end");
//				throw new RuntimeException("zidingyi ex");
				return null;
			}
		};
		
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				System.err.println("run begin");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.err.println("run end");
//				throw new RuntimeException("run err");
			}
		};
		
		Future<String> f = es.submit(c);
		Future<?> fr = es.submit(r);
		
		Object object = fr.get();
		System.out.println(object);
		
		System.err.println(f.get());
		
		while(true){
			if(f.isDone()){
				System.out.println("done");
				break;
			}
		}
		System.err.println("main is end");
	}
	
}
