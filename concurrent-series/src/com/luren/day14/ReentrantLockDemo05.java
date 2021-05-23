package com.luren.day14;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 关于线程等待/唤醒的方法
 * <p>
 * 第二种，使用 ReentrantLock 中的 Condition
 *
 * 调用condition中线程等待和唤醒的方法的前提是必须要先获取lock的锁，要不程序将抛出异常
 *
 * Exception in thread "thread-1" java.lang.IllegalMonitorStateException
 * 	at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(ReentrantLock.java:154)
 * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(AbstractQueuedSynchronizer.java:1265)
 * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer.fullyRelease(AbstractQueuedSynchronizer.java:1728)
 * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2041)
 * 	at com.luren.day14.ReentrantLockDemo05$1.run(ReentrantLockDemo05.java:35)
 * Exception in thread "main" java.lang.IllegalMonitorStateException
 * 	at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.signal(AbstractQueuedSynchronizer.java:1944)
 * 	at com.luren.day14.ReentrantLockDemo05.main(ReentrantLockDemo05.java:49)
 *
 */
public class ReentrantLockDemo05 {
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + "获取到锁，开始等待");
                try {
                    condition.await();
                    System.out.println(System.currentTimeMillis() + "，" + this.getName() + "被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                }

            }
        };
        t1.setName("thread-1");
        t1.start();

        TimeUnit.SECONDS.sleep(3);
        try{
            condition.signal();
        }finally {
        }

    }

}
