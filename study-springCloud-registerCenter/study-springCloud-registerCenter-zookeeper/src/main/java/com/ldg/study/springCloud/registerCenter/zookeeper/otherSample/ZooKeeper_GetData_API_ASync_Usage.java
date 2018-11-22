package com.ldg.study.springCloud.registerCenter.zookeeper.otherSample;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZooKeeper_GetData_API_ASync_Usage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        String path = "/zk-book4";
        zooKeeper = new ZooKeeper("localhost:2181",
                5000,
                new ZooKeeper_GetData_API_ASync_Usage());
        connectedSemaphore.await();

        zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getData(path, true, new IDataCallback(), null);
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
                    zooKeeper.getData(watchedEvent.getPath(),true,null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (KeeperException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class IDataCallback implements AsyncCallback.DataCallback{

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        System.out.println(rc+" "+path+" "+new String(data));
        System.out.println(stat);
    }
}
