package com.ldg.study.springCloud.registerCenter.zookeeper.otherSample;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZooKeeper_GetData_API_Sync_Usage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        String path = "/zk-book3";
        zooKeeper = new ZooKeeper("localhost:2181",
                5000,
                new ZooKeeper_GetData_API_Sync_Usage());
        connectedSemaphore.await();

        zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        System.out.println(new String(zooKeeper.getData(path, true, stat)));
        System.out.println(stat);

        zooKeeper.setData(path, "123".getBytes(), -1);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(Event.KeeperState.SyncConnected == watchedEvent.getState()){
            if(Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()){
                connectedSemaphore.countDown();
            }else if(watchedEvent.getType() == Event.EventType.NodeDataChanged){
                try{
                    System.out.println(new String(zooKeeper.getData(watchedEvent.getPath(),true,stat)));
                    System.out.println(stat);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (KeeperException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
