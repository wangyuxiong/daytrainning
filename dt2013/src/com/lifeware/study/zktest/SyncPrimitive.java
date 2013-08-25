package com.lifeware.study.zktest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.data.Stat;

public class SyncPrimitive implements Watcher{

	private static ZooKeeper zk = null;
	private static Integer mutex;
	public String root;
	
	public SyncPrimitive(String address){
		if(zk == null){
			System.out.println("Start ZK:");
			try {
				zk = new ZooKeeper(address,2000,this);
				mutex = new Integer(-1);
				System.out.println("Finished ZK:" + zk);
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	synchronized public void process(WatchedEvent event){
		synchronized(mutex){
			mutex.notify();
		}
	}
	
	static public class Barrier extends SyncPrimitive{
		private String name;
		private int size;
		
		Barrier(String address,String root,int size) throws KeeperException, InterruptedException{
			super(address);
			this.root = root;
			this.size = size;
			
			//create barrier node
			Stat stat = zk.exists(root, false);
			if(stat == null){
				zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			
			//my node
			try{
				name = new String(InetAddress.getLocalHost().getCanonicalHostName().toString());
			}
			catch(UnknownHostException e){
				System.out.println(e.toString());
			}
		}
		
		//join barrier
		boolean enter() throws KeeperException, InterruptedException {
			zk.create(root + "/" + name, new byte[0], Ids.READ_ACL_UNSAFE, CreateMode.PERSISTENT);
			while(true){
				synchronized(mutex){
					List<String> list = zk.getChildren(root, true);
					if(list.size() < size){
						mutex.wait();
					}else{
						return true;
					}
				}
			}
			
		}
		
		boolean leave() throws KeeperException, InterruptedException {
			zk.delete(root+"/"+name,0);
			while(true){
				synchronized(mutex){
					List<String> list = zk.getChildren(root, true);
					if(list.size() > 0){
						mutex.wait();
					}else{
						return true;
					}
				}
			}	
		}
	}
	
	
	static public class Queue extends SyncPrimitive{
		Queue(String address,String name){
			super(address);
			this.root = name;
			//Create zk node
			if(zk != null){
				try{
					Stat s = zk.exists(root, false);
					if (s == null){
						zk.create(root, new byte[0],Ids.OPEN_ACL_UNSAFE , CreateMode.PERSISTENT);
					}
				}
				catch(KeeperException e){
					System.out.println("Keeper exception when instantiating queue: " + e.toString());
				}
				catch(InterruptedException e){
					System.out.println("Interrupted exception");
				}
			}
		}
		
		boolean produce(int i) throws KeeperException, InterruptedException{
			ByteBuffer b = ByteBuffer.allocate(4);
			byte[] value;
			b.putInt(i);
			value = b.array();
			zk.create(root+"/element", value, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
			return true;
		}
		
		int consume() throws KeeperException, InterruptedException{
			int retvalue = -1;
			Stat stat = null;
			while(true){
				synchronized(mutex){
					List<String> list = zk.getChildren(root, true);
					if(list.size() == 0){
						mutex.wait();
					}
					else{
						Integer min = new Integer(list.get(0).substring(7));
						for(String s : list){
							Integer tempValue = new Integer(s.substring(7));
							if (tempValue < min){
								min = tempValue;
							}
						}
						System.out.println("Temp:" + root + "/element" + min);
						byte[] b = zk.getData(root + "/element" + min, false, stat);
						zk.delete(root + "/element" + min, 0);
						ByteBuffer buffer = ByteBuffer.wrap(b);
                        retvalue = buffer.getInt();

                        return retvalue;
					}
				}
			}
		}
	}
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public static void main(String[] args) throws KeeperException, InterruptedException {
		// TODO Auto-generated method stub
		//barriarTest();
		queueTest("p");
	}
	
	public static void barriarTest() throws KeeperException, InterruptedException{
		Barrier b = new Barrier("localhost:2181","/barroot",new Integer(10));
		boolean flag = b.enter();
		System.out.println("Enter barriar:" + 10);
		if(!flag){
			System.out.println("Error Enter barriar:" + 10);
		}
		
		Random rand = new Random();
		int r = rand.nextInt(100);
		for(int i=0;i<r;i++){
			Thread.sleep(100);
		}
		b.leave();
		System.out.println("Left barrier");
	}
	
	public static void queueTest(String arg) throws KeeperException, InterruptedException{
		Queue q = new Queue("localhost:2181","/queueroot");
		Integer max = new Integer(10);
		if(arg.equals("p")){
			System.out.println("producer");
			for(int i=0;i<max;i++){
				q.produce(i);
			}
		}
		else{
			System.out.println("consumer");
			for(int i=0;i<max;i++){
				try{
					q.consume();
				}
				catch(KeeperException e){
					i--;
				}
			}
		}
	}

}
