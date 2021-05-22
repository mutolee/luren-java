package com.luren.day12;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟 ReentrantLock 中断 的例子
 */
public class ReentrantLockDemo05 {

    // 创建两把锁
    static ReentrantLock lock01 = new ReentrantLock();
    static ReentrantLock lock02 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thrd t1 = new Thrd(1);
        t1.setName("thread-1");
        t1.start();
        Thrd t2 = new Thrd(2);
        t2.setName("thread-2");
        t2.start();

        // 以上情况，程序无法正常结束，（死锁）
        // thread-1 获取 lock02 的时候，lock02 被 thread-2 持有
        // thread-2 获取 lock01 的时候，lock01 被 thread-1 持有
        // 所以 以上 代码程序无法结束
        // ==============================================================

        // 让主程序 5 秒后，终止 thread-2，
        // thread-2 释放了 持有的 lock02，thread-2 结束，
        // 这时候 lock02 被 thread-1 持有
        // 然后 thread-1 正常执行，程序结束
        TimeUnit.SECONDS.sleep(5);
        t2.interrupt();
    }

    static class Thrd extends Thread {
        int lock;

        Thrd(int lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            if (lock == 1) {
                try {
                    // 获取 lock01 的可中断锁
                    lock01.lockInterruptibly();
                    System.out.println("lock01被" + this.getName() + "持有");
                    // 当前线程休息 1 秒后
                    TimeUnit.SECONDS.sleep(1);
                    // 获取 lock02 的可中断锁
                    lock02.lockInterruptibly();
                    System.out.println("lock02被" + this.getName() + "持有");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(this.getName() + "-线程中断标志：" + this.isInterrupted());
                } finally {
                    // 如果 lock01 被当前线程持有中
                    if (lock01.isHeldByCurrentThread()) {
                        lock01.unlock();
                        System.out.println("lock01被" + this.getName() + "释放");
                    }
                    // 如果 lock02 被当前线程持有中
                    if (lock02.isHeldByCurrentThread()) {
                        lock02.unlock();
                        System.out.println("lock02被" + this.getName() + "释放");
                    }
                }
            } else if (lock == 2) {
                try {
                    // 获取 lock02 的可中断锁
                    lock02.lockInterruptibly();
                    System.out.println("lock02被" + this.getName() + "持有");
                    // 当前线程休息 1 秒后
                    TimeUnit.SECONDS.sleep(1);
                    // 获取 lock01 的可中断锁
                    lock01.lockInterruptibly();
                    System.out.println("lock01被" + this.getName() + "持有");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(this.getName() + "-线程中断");
                } finally {
                    // 如果 lock01 被当前线程持有中
                    if (lock01.isHeldByCurrentThread()) {
                        lock01.unlock();
                        System.out.println("lock01被" + this.getName() + "释放");
                    }
                    // 如果 lock02 被当前线程持有中
                    if (lock02.isHeldByCurrentThread()) {
                        lock02.unlock();
                        System.out.println("lock02被" + this.getName() + "释放");
                    }
                }
            }
        }
    }

}
