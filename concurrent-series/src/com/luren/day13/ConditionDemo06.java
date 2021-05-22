package com.luren.day13;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition awaitNanos() 超时返回 例子
 */
public class ConditionDemo06 {
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thrd t1 = new Thrd();
        t1.start();
    }

    static class Thrd extends Thread {
        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println(System.currentTimeMillis() + "：" + this.getName() + "，start");
                // 等待被唤醒，5 秒后，自动唤醒，
                // 返回结果为负数，表示超时之后返回的
                long l = condition.awaitNanos(TimeUnit.SECONDS.toNanos(5));
                System.out.println("等待状态：" + l);
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
