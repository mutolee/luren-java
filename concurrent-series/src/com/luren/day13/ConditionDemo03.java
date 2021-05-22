package com.luren.day13;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 被中断 例子
 */
public class ConditionDemo03 {
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thrd t1 = new Thrd();
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("1. 中断标识：" + t1.isInterrupted());
        // 给 线程 t1 发送中断信息
        t1.interrupt();
        System.out.println("2. 中断标识：" + t1.isInterrupted());
    }

    static class Thrd extends Thread{
        @Override
        public void run() {
            lock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                System.out.println("中断标识：" + this.isInterrupted());
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
}
