package com.ldg.study.springCloud.registerCenter.zookeeper.otherSample;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZooKeeper_Contructor_Usage implements Watcher {

    /**
     * 调用await()阻塞，当减到0时，恢复
     * http://www.importnew.com/15731.html
     */
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException {
        ZooKeeper zookeeper = new ZooKeeper("127.0.0.1:2181",5000,new ZooKeeper_Contructor_Usage());
        System.out.println(zookeeper.getState());
        try{
            connectedSemaphore.await();
        }catch (Exception e){
            System.out.println("zk session established");
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("recieve watched event:"+watchedEvent);
        if(Event.KeeperState.SyncConnected == watchedEvent.getState()){
            connectedSemaphore.countDown();
        }
    }
}
