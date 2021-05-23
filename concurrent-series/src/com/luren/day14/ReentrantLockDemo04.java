package com.luren.day14;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 关于线程等待/唤醒的方法
 * <p>
 * 第二种，使用 ReentrantLock 中的 Condition
 */
public class ReentrantLockDemo04 {
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                lock.lock();
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + "获取到锁，开始等待");
                try {
                    condition.await();
                    System.out.println(System.currentTimeMillis() + "，" + this.getName() + "被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }

            }
        };
        t1.setName("thread-1");
        t1.start();

        TimeUnit.SECONDS.sleep(3);
        lock.lock();
        try{
            condition.signal();
        }finally {
            lock.unlock();
        }

    }

}
