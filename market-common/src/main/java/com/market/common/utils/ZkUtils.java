package com.market.common.utils;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZkUtils {

	public static void main(String[] args) throws IOException {
		
		ZooKeeper zk = new ZooKeeper("", 10, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				
			}
		});
		
	}
	
}
