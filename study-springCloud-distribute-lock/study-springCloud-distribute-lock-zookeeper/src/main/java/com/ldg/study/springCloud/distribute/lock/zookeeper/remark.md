Zookeeper分布锁
    
    首先创建加锁标志文件，如果需要等待其他锁，则添加监听后等待通知或者超时，当有锁释放，无须争抢，按照节点顺序，依次通知使用者。

Znode分为四种类型：

    1.持久节点 （PERSISTENT）
        默认的节点类型。创建节点的客户端与zookeeper断开连接后，该节点依旧存在 。

    2.持久节点顺序节点（PERSISTENT_SEQUENTIAL）
        所谓顺序节点，就是在创建节点时，Zookeeper根据创建的时间顺序给该节点名称进行编号：
    
    3.临时节点（EPHEMERAL） 
        和持久节点相反，当创建节点的客户端与zookeeper断开连接后，临时节点会被删除：
    
    4.临时顺序节点（EPHEMERAL_SEQUENTIAL） 
        顾名思义，临时顺序节点结合和临时节点和顺序节点的特点：在创建节点时，Zookeeper根据创建的时间顺序给该节点名称进行编号；当创建节点的客户端与zookeeper断开连接后，临时节点会被删除
        
 Zookeeper分布式锁的原理:
 
    1、获取锁
        在Zookeeper当中创建一个持久节点ParentLock。当第一个客户端想要获得锁时，需要在ParentLock这个节点下面创建一个临时顺序节点 Lock1。
        Client1查找ParentLock下面所有的临时顺序节点并排序，判断自己所创建的节点Lock1是不是顺序最靠前的一个。如果是第一个节点，则成功获得锁。
        这时候，如果再有一个客户端 Client2 前来获取锁，则在ParentLock下载再创建一个临时顺序节点Lock2。
        Client2查找ParentLock下面所有的临时顺序节点并排序，判断自己所创建的节点Lock2是不是顺序最靠前的一个，结果发现节点Lock2并不是最小的。
        于是，Client2向排序仅比它靠前的节点Lock1注册Watcher，用于监听Lock1节点是否存在。这意味着Client2抢锁失败，进入了等待状态。
        这时候，如果又有一个客户端Client3前来获取锁，则在ParentLock下载再创建一个临时顺序节点Lock3。
        这样一来，Client1得到了锁，Client2监听了Lock1，Client3监听了Lock2。这恰恰形成了一个等待队列，很像是Java当中ReentrantLock所依赖的AQS（AbstractQueuedSynchronizer）。