package com.ldg.study.springCloud.registerCenter.zookeeper.otherSample;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class AuthSample_Delete {
    final static String PATH = "/zk-book-auth-test";
    final static String PATH2 = "/zk-book-auth-test/child";
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper1 = new ZooKeeper("localhost:2181",5000,null);
        zooKeeper1.addAuthInfo("digest","foo:true".getBytes());
        zooKeeper1.create(PATH,"".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        zooKeeper1.create(PATH2,"".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL,CreateMode.EPHEMERAL);

        try{
            ZooKeeper zooKeeper2 = new ZooKeeper("localhost:2181",5000,null);
            zooKeeper2.delete(PATH2,-1);
        }catch (Exception e){
            System.out.println("删除节点失败:"+e.getMessage());
        }

        ZooKeeper zooKeeper3 = new ZooKeeper("localhost:2181",5000,null);
        zooKeeper3.addAuthInfo("digest","foo:true".getBytes());
        zooKeeper3.delete(PATH2,-1);
        System.out.println("成功删除："+PATH2);

        zooKeeper3.delete(PATH, -1);
        System.out.println("成功删除："+PATH);
    }
}
