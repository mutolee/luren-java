package com.luren.day12;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 ReentrantLock 对临界资源进行操作 公平锁的例子 的例子
 *
 * console 输入内容：
 *
 * 当前线程名称：Thread-0
 * 当前线程名称：Thread-1
 * 当前线程名称：Thread-2
 * 当前线程名称：Thread-0
 * 当前线程名称：Thread-1
 * 当前线程名称：Thread-2
 * 当前线程名称：Thread-0
 * 当前线程名称：Thread-1
 * 当前线程名称：Thread-2
 *
 * 说明锁的获取是按线程启动顺序来的
 *
 */
public class ReentrantLockDemo04 {
    static int num = 0;
    // 设置公平锁
    // 默认非公平锁，可以 改成 false，多试几遍看看效果
    static ReentrantLock lock = new ReentrantLock(true);

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
        for (int i = 0; i < 10; i++) {
            lock.lock();
            try {
                System.out.println("当前线程名称：" + Thread.currentThread().getName());
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
