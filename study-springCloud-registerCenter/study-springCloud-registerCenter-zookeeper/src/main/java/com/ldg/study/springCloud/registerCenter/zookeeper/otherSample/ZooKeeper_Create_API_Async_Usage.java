package com.ldg.study.springCloud.registerCenter.zookeeper.otherSample;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class ZooKeeper_Create_API_Async_Usage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181",
                5000,
                new ZooKeeper_Create_API_Async_Usage());

        connectedSemaphore.await();

        zooKeeper.create("/zk-test-ephemeral-",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                new IStringCallback(),
                "I am context");

        zooKeeper.create("/zk-test-ephemeral-",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                new IStringCallback(),
                "I am context");

        zooKeeper.create("/zk-test-ephemeral-",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL,
                new IStringCallback(),
                "I am context");

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(Event.KeeperState.SyncConnected == watchedEvent.getState()){
            connectedSemaphore.countDown();
        }
    }
}

/**
 * resultCode 服务端的响应码，0 成功 -4 连接断开 -110 节点存在 -112 会话过期
 * path 传入的路径
 * ctx 参数值
 * name 实际的节点值
 */
class IStringCallback implements AsyncCallback.StringCallback {
    @Override
    public void processResult(int resultCode, String name, Object o, String realName) {
        System.out.println("Create path result: ["+resultCode+", "+name+", "+o+", real path name: "+realName);
    }
}
