package com.luren.day13;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 例子
 * <p>
 * 用法 和 synchronized 中 wait() notify() 差不多
 */
public class ConditionDemo02 {

    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thrd01 t1 = new Thrd01();
        t1.setName("thread-1");
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        Thrd02 t2 = new Thrd02();
        t2.setName("thread-2");
        t2.start();
    }

    static class Thrd01 extends Thread {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + "，" + this.getName() + "准备获取锁...");
            lock.lock();
            System.out.println(System.currentTimeMillis() + "，" + this.getName() + "获取到了锁");
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + "让出锁，自己挂起");
                // 线程等待
                condition.await();
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + "获取到 CPU 开始继续执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + "准备释放锁");
                lock.unlock();
            }
            System.out.println(System.currentTimeMillis() + "，" + this.getName() + "释放了锁...end");
        }
    }

    static class Thrd02 extends Thread {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + "，" + this.getName() + "准备获取锁...");
            lock.lock();
            System.out.println(System.currentTimeMillis() + "，" + this.getName() + "获取到了锁");
            condition.signal();
            System.out.println(System.currentTimeMillis() + "，" + this.getName() + "通知其他线程不要挂起了，请争取 CPU 继续执行");
            try {
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + "休息 2 秒");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + "准备释放锁");
                lock.unlock();
            }
            System.out.println(System.currentTimeMillis() + "，" + this.getName() + "释放了锁...end");
        }
    }
}
