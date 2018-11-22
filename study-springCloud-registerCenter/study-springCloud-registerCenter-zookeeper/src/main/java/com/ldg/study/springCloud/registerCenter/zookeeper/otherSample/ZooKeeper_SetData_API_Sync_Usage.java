package com.ldg.study.springCloud.registerCenter.zookeeper.otherSample;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZooKeeper_SetData_API_Sync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String path = "/zk-book5";
        zooKeeper = new ZooKeeper("localhost:2181",
                5000,
                new ZooKeeper_SetData_API_Sync_Usage());
        connectedSemaphore.await();
        zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getData(path, true, null);
        Stat stat = zooKeeper.setData(path, "456".getBytes(), -1);
        System.out.println(stat);
        Stat stat2 = zooKeeper.setData(path, "456".getBytes(), stat.getVersion());
        System.out.println(stat2);

        try{
            zooKeeper.setData(path, "456".getBytes(), stat.getVersion());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        if(Event.KeeperState.SyncConnected == event.getState()){
            if(Event.EventType.None == event.getType() && null == event.getPath()){
                connectedSemaphore.countDown();
            }
        }
    }
}
