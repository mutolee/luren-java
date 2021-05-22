package com.luren.day12;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟 ReentrantLock 限时获取锁 的例子
 * <p>
 * tryLock() 立即返回获取锁结果
 * tryLock(2) 坚持获取 2 秒，然后返回获取锁结果
 */
public class ReentrantLockDemo06 {

    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        Thrd t1 = new Thrd();
        t1.setName("thread-1");
        t1.start();
        Thrd t2 = new Thrd();
        t2.setName("thread-2");
        t2.start();
    }

    static class Thrd extends Thread {
        @Override
        public void run() {
            System.out.println(this.getName() + "开始获取锁...");
            try {
                // 限时获取锁
                if (lock.tryLock(4, TimeUnit.SECONDS)) {
                    System.out.println(this.getName() + "获取到了锁");
                    try {
                        // 模拟 拿着锁用了 3 秒
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(this.getName() + "没有获取到了锁");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 如果 锁 被当前线程持有
                if (lock.isHeldByCurrentThread()) {
                    System.out.println(this.getName() + "用完结束，释放了锁");
                    lock.unlock();
                }
            }

        }
    }

}
