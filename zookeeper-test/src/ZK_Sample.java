
import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;;

public class ZK_Sample {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ZooKeeper zk = new ZooKeeper("localhost:2181",2000,new Watcher(){
			  // 监控所有被触发的事件
            public void process(WatchedEvent event) { 
                System.out.println("已经触发了" + event.getType() + "事件"); 
            } 
         });
		if(zk.exists("/test",false) != null){
			zk.delete("/test", -1);
		}
		zk.create("/test", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		//创建一个子目录节点
		zk.create("/test/testChildPathOne","testChildPathOne".getBytes(),Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT_SEQUENTIAL);
		System.out.println(zk.getData("/test", false, null));
		
	}

}
