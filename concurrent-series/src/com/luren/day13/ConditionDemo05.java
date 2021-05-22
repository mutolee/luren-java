package com.luren.day13;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition await() 超时前被唤醒 例子
 */
public class ConditionDemo05 {
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thrd t1 = new Thrd();
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        lock.lock();
        try {
            // 唤醒 condition 上的等待线程
            condition.signal();
        } finally {
            lock.unlock();
        }


    }

    static class Thrd extends Thread {
        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println(System.currentTimeMillis() + "：" + this.getName() + "，start");
                // 等待被唤醒，5 秒后，自动唤醒
                boolean await = condition.await(5, TimeUnit.SECONDS);
                System.out.println("等待状态：" + await);
                System.out.println(System.currentTimeMillis() + "：" + this.getName() + "，end");
            } catch (InterruptedException e) {
                System.out.println("中断标识：" + this.isInterrupted());
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
