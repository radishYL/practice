package com.market.test;

import com.market.common.nio.NioServer;

public class NioTest {

	public static void main(String[] args) {
		NioServer server = new NioServer(8089, 10, 10);
		server.start();
	}
	
}
