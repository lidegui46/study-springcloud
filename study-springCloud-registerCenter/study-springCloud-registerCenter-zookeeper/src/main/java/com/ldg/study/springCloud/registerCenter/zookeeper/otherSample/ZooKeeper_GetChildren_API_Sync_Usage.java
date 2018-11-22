package com.ldg.study.springCloud.registerCenter.zookeeper.otherSample;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZooKeeper_GetChildren_API_Sync_Usage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper("localhost:2181",
                5000,
                new ZooKeeper_GetChildren_API_Sync_Usage());

        connectedSemaphore.await();

        String path = "/zk-book1";
        zooKeeper.create(path,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create(path+"/c1","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);

        List<String> childrenList = zooKeeper.getChildren(path, true);
        System.out.println(childrenList);

        zooKeeper.create(path+"/c2","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);

        zooKeeper.create(path+"/c3","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(Event.KeeperState.SyncConnected == watchedEvent.getState()){
            if(Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()){
                connectedSemaphore.countDown();
            }else if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                try{
                    System.out.println("ReGet Child:"+zooKeeper.getChildren(watchedEvent.getPath(),true));
                }catch (Exception e){}
            }
        }
    }
}
