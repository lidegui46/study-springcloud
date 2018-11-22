package com.ldg.study.springCloud.registerCenter.zookeeper.otherSample;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZooKeeper_Contructor_Usage_With_SID_PASSWD implements Watcher {
    private static CountDownLatch conntedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {

        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181",
                5000,
                new ZooKeeper_Contructor_Usage_With_SID_PASSWD());

        conntedSemaphore.await();

        long sessionId = zooKeeper.getSessionId();
        byte[] passwd  = zooKeeper.getSessionPasswd();

        zooKeeper = new ZooKeeper("localhost:2181",
                5000,
                new ZooKeeper_Contructor_Usage_With_SID_PASSWD(),
                1L,
                "test".getBytes());
        zooKeeper = new ZooKeeper("localhost:2181",
                5000,
                new ZooKeeper_Contructor_Usage_With_SID_PASSWD(),
                sessionId,
                passwd);

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Recieve watched event:" + watchedEvent.getState());

        if(Event.KeeperState.SyncConnected == watchedEvent.getState()){
            conntedSemaphore.countDown();
        }
    }
}
