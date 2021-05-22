package com.luren.day12;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 ReentrantLock 对临界资源进行操作 的例子
 */
public class ReentrantLockDemo03 {
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
            // 当一个线程进入的时候，会执行2次获取锁的操作，运行程序可以正常结束，并输出和期望值一样的30000，
            // 假如ReentrantLock是不可重入的锁，那么同一个线程第2次获取锁的时候由于前面的锁还未释放而导致死锁，
            // 程序是无法正常结束的。ReentrantLock命名也挺好的Re entrant Lock，和其名字一样，可重入锁。
            lock.lock();
            lock.lock();
            try {
                num++;
            } finally {
                // lock.unlock()一定要放在finally中，否则，
                // 若程序出现了异常，锁没有释放，那么其他线程就再也没有机会获取这个锁了
                lock.unlock();
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
