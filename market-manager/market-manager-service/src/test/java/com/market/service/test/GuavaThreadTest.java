package com.market.service.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class GuavaThreadTest {

	public static void main(String[] args) throws InterruptedException {
		
		ListeningExecutorService les = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
		CountDownLatch latch = new CountDownLatch(1);
		ListenableFuture<String> lf = les.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(10000);
				return "end";
			}
		});
		
		System.out.println(lf.isDone());
		
		List<String> results = new ArrayList<>();
		// 该方法会新开一个线程处理线程返回的结果
		Futures.addCallback(lf, new FutureCallback<String>() {

			@Override
			public void onSuccess(String result) {
				System.err.println(result);
				results.add(result);
				latch.countDown();
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("ex--"+t);
				latch.countDown();
			}
		});
		
		System.out.println("main 未阻塞");
//		les.shutdown();
//		les.awaitTermination(60, TimeUnit.SECONDS);
		latch.await();
		System.out.println("main 开始休眠");
		
		Thread.sleep(10000);
		
	}
	
	
}
