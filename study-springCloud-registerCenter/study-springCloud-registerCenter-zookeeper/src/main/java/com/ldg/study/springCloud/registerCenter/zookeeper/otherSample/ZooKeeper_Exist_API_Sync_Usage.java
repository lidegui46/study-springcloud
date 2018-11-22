package com.ldg.study.springCloud.registerCenter.zookeeper.otherSample;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZooKeeper_Exist_API_Sync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String path = "/zk-book6";
        zooKeeper = new ZooKeeper("localhost:2181",5000, new ZooKeeper_Exist_API_Sync_Usage());
        connectedSemaphore.countDown();
        zooKeeper.exists(path, true);
        zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        zooKeeper.setData(path, "123".getBytes(), -1);
        zooKeeper.create(path+"/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        zooKeeper.delete(path+"/c1",-1);
        zooKeeper.delete(path,-1);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        try{
            if(Event.KeeperState.SyncConnected == event.getState()){
                if(Event.EventType.None == event.getType() && null == event.getPath()){
                    connectedSemaphore.countDown();
                }else if(Event.EventType.NodeCreated == event.getType()){
                    System.out.println("node "+event.getPath()+" created");
                    zooKeeper.exists(event.getPath(), true);
                }else if(Event.EventType.NodeDeleted == event.getType()){
                    System.out.println("node "+event.getPath()+" deleted");
                    zooKeeper.exists(event.getPath(), true);
                }else if(Event.EventType.NodeDataChanged == event.getType()){
                    System.out.println("node "+event.getPath()+" changed");
                    zooKeeper.exists(event.getPath(), true);
                }
            }
        }catch (Exception e){}
    }
}
