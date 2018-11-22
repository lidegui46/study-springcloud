package com.ldg.study.springCloud.registerCenter.zookeeper.otherSample;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class AuthSample {
    final static String PATH = "/zk-book-auth-test";
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181",5000,null);
        zooKeeper.addAuthInfo("digest", "foo:true".getBytes());
        zooKeeper.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
