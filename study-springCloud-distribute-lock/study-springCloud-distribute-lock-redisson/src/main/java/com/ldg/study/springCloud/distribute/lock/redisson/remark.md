 Redis分布式锁
    
    必须使用者自己间隔时间轮询去尝试加锁，当锁被释放后，存在多线程去争抢锁，并且可能每次间隔时间去尝试锁的时候，都不成功，对性能浪费很大。