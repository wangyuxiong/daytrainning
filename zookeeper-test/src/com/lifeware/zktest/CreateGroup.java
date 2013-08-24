package com.lifeware.zktest;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class CreateGroup implements Watcher{
	private static final int SESSION_TIMEOUT = 2000;
	private ZooKeeper zk;
	private CountDownLatch connectedSignal = new CountDownLatch(1);
	
	public void connect(String host) throws IOException,InterruptedException{
		zk = new ZooKeeper(host,SESSION_TIMEOUT,this);
		connectedSignal.await();
	}
	

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		if(event.getState() == KeeperState.SyncConnected){
			connectedSignal.countDown();
		}
	}
	
	public void create(String groupName) throws KeeperException,InterruptedException{
		String path = "/" + groupName;
		if(zk.exists(path, false) != null){
			zk.delete(path, -1);
		}
		String createPath = zk.create(path, path.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("Created " + createPath);
	}

	public void close() throws InterruptedException{
		zk.close();
	}
	
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws KeeperException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		// TODO Auto-generated method stub
		CreateGroup createGroup = new CreateGroup();
		String host = "127.0.0.1:2181";
		createGroup.connect(host);
		createGroup.create("testroot");
		createGroup.close();
	}
}
