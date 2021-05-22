package com.luren.day12;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 ReentrantLock 对临界资源进行操作 的例子
 */
public class ReentrantLockDemo02 {
    static int num = 0;
    static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thrd t1 = new Thrd();
        t1.start();
        Thrd t2 = new Thrd();
        t2.start();
        Thrd t3 = new Thrd();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(num);
    }

    static void add() {
        for (int i = 0; i < 10000; i++) {
            lock.lock();
            try {
                num++;
            } finally {
                // lock.unlock()一定要放在finally中，否则，
                // 若程序出现了异常，锁没有释放，那么其他线程就再也没有机会获取这个锁了
                lock.unlock();
            }
        }
    }

    static class Thrd extends Thread {
        @Override
        public void run() {
            add();
        }
    }
}
