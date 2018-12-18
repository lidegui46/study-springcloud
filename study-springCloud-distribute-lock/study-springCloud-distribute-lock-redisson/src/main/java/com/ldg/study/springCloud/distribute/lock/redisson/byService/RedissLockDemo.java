package com.ldg.study.springCloud.distribute.lock.redisson.byService;

import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁Demo
 *
 * @author 科帮网 By https://blog.52itstyle.com
 */
public class RedissLockDemo {
    /**
     * 可重入锁（Reentrant Lock）
     * Redisson的分布式可重入锁RLock Java对象实现了java.util.concurrent.locks.Lock接口，同时还支持自动过期解锁
     *
     * @param redisson
     */
    public void reentrantLock(RedissonClient redisson) {
        RLock lock = redisson.getLock("anyLock");
        try {
            // 1. 最常见的使用方法
            // lock.lock();
            // 2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
            // lock.lock(10, TimeUnit.SECONDS);
            // 3. 尝试加锁，最多等待3秒，上锁以后10秒自动解锁
            boolean res = lock.tryLock(3, 10, TimeUnit.SECONDS);
            if (res) { // 成功
                // do your business
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Redisson同时还为分布式锁提供了异步执行的相关方法
     *
     * @param redisson
     */
    public void asyncReentrantLock(RedissonClient redisson) {
        RLock lock = redisson.getLock("anyLock");
        try {
            // 1. 最常见的使用方法
            //lock.lockAsync();
            // 2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
            //lock.lockAsync(10, TimeUnit.SECONDS);
            // 3. 尝试加锁，最多等待3秒，上锁以后10秒自动解锁
            Future<Boolean> res = lock.tryLockAsync(3, 10, TimeUnit.SECONDS);
            if (res.get()) {
                // do your business
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 公平锁（Fair Lock）
     * Redisson分布式可重入公平锁也是实现了java.util.concurrent.locks.Lock接口的一种RLock对象。
     * 在提供了自动过期解锁功能的同时，保证了当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程。
     *
     * @param redisson
     */
    public void fairLock(RedissonClient redisson) {
        RLock fairLock = redisson.getFairLock("anyLock");
        try {
            // 1. 最常见的使用方法
            //fairLock.lock();
            // 2. 支持过期解锁功能, 10秒钟以后自动解锁,无需调用unlock方法手动解锁
            //fairLock.lock(10, TimeUnit.SECONDS);
            // 3. 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = fairLock.tryLock(100, 10, TimeUnit.SECONDS);
            if (res) {
                // do your business
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            fairLock.unlock();
        }
    }

    /**
     * 异步公平锁（Fair Lock）
     * Redisson分布式可重入公平锁也是实现了java.util.concurrent.locks.Lock接口的一种RLock对象。
     * 在提供了自动过期解锁功能的同时，保证了当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程。
     *
     * @param redisson
     */
    public void asyncFairLock(RedissonClient redisson) {
        RLock fairLock = redisson.getFairLock("anyLock");
        try {
            // 1. 最常见的使用方法
            //fairLock.lockAsync();
            // 2. 支持过期解锁功能, 10秒钟以后自动解锁,无需调用unlock方法手动解锁
            //fairLock.lockAsync(10, TimeUnit.SECONDS);
            // 3. 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            Future<Boolean> res = fairLock.tryLockAsync(100, 10, TimeUnit.SECONDS);
            if (res.get()) {
                // do your business
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            fairLock.unlock();
        }
    }

    /**
     * 联锁（MultiLock）
     * Redisson的RedissonMultiLock对象可以将多个RLock对象关联为一个联锁，每个RLock对象实例可以来自于不同的Redisson实例
     *
     * @param redisson1
     * @param redisson2
     * @param redisson3
     */
    public void multiLock(RedissonClient redisson1, RedissonClient redisson2, RedissonClient redisson3) {
        RLock lock1 = redisson1.getLock("lock1");
        RLock lock2 = redisson2.getLock("lock2");
        RLock lock3 = redisson3.getLock("lock3");
        RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);
        try {
            // 同时加锁：lock1 lock2 lock3, 所有的锁都上锁成功才算成功。
            lock.lock();
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
            if (res) {
                // do your business
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 红锁（RedLock）
     * Redisson的RedissonRedLock对象实现了Redlock介绍的加锁算法。该对象也可以用来将多个RLock对象关联为一个红锁，每个RLock对象实例可以来自于不同的Redisson实例
     *
     * @param redisson1
     * @param redisson2
     * @param redisson3
     */
    public void redLock(RedissonClient redisson1, RedissonClient redisson2, RedissonClient redisson3) {
        RLock lock1 = redisson1.getLock("lock1");
        RLock lock2 = redisson2.getLock("lock2");
        RLock lock3 = redisson3.getLock("lock3");
        RedissonRedLock lock = new RedissonRedLock(lock1, lock2, lock3);
        try {
            // 同时加锁：lock1 lock2 lock3, 红锁在大部分节点上加锁成功就算成功。
            lock.lock();
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
            if (res) {
                // do your business
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 读写锁（ReadWriteLock） - 读锁
     *
     * @param redisson
     */
    public void readLock(RedissonClient redisson) {
        RReadWriteLock rlock = (RReadWriteLock) redisson.getLock("anyRWLock");
        try {
            //rlock.readLock().lock();
            //rlock.readLock().lock(10, TimeUnit.SECONDS);
            boolean res = rlock.readLock().tryLock(100, 10, TimeUnit.SECONDS);
            if (res) {
                // do your business
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            rlock.readLock().unlock();
        }
    }

    /**
     * 读写锁（ReadWriteLock） - 写锁
     *
     * @param redisson
     */
    public void writeLock(RedissonClient redisson) {
        RReadWriteLock wlock = (RReadWriteLock) redisson.getLock("anyRWLock");
        try {
            //wlock.writeLock().lock();
            //wlock.writeLock().lock(10, TimeUnit.SECONDS);
            boolean res = wlock.writeLock().tryLock(100, 10, TimeUnit.SECONDS);
            if (res) {
                // do your business
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            wlock.readLock().unlock();
        }
    }

    /**
     * 信号量（Semaphore）   待验证
     *
     * @param redisson
     */
    public void semaphore(RedissonClient redisson) {
        RSemaphore semaphore = redisson.getSemaphore("semaphore");
        try {
            semaphore.acquire();
            //semaphore.acquire(23);
            //semaphore.acquireAsync();
            //semaphore.tryAcquire();
            //semaphore.tryAcquireAsync();
            //semaphore.tryAcquire(23, TimeUnit.SECONDS);
            //semaphore.tryAcquireAsync(23, TimeUnit.SECONDS);

            semaphore.release(10);
            //semaphore.release();
            //semaphore.releaseAsync();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
    }

    /**
     * 可过期性信号量（PermitExpirableSemaphore）   待验证
     *
     * @param redisson
     */
    public void permitExpirableSemaphore(RedissonClient redisson) {
        RPermitExpirableSemaphore semaphore = redisson.getPermitExpirableSemaphore("mySemaphore");
        try {
            String permitId = semaphore.acquire();
            // 获取一个信号，有效期只有2秒钟。
            //String permitId = semaphore.acquire(2, TimeUnit.SECONDS);

            semaphore.release(permitId);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }

    /**
     * 闭锁（CountDownLatch）   待验证
     *
     * @param redisson
     */
    public void countDownLatch(RedissonClient redisson) {
        RCountDownLatch latch = redisson.getCountDownLatch("anyCountDownLatch");
        try {
            latch.trySetCount(1);
            latch.await();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }

        // 在其他线程或其他JVM里
        RCountDownLatch latch1 = redisson.getCountDownLatch("anyCountDownLatch");
        latch1.countDown();
    }
}