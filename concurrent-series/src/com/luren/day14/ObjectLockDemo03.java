package com.luren.day14;

import java.util.concurrent.TimeUnit;

/**
 * 关于线程等待/唤醒的方法
 * <p>
 * 第一种，synchronized 中
 * wait() 和 notify()
 *
 * notify() 必须在 wait() 之后执行，否则无法唤醒等待的线程，程序无法结束
 *
 *
 */
public class ObjectLockDemo03 {
    static Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object) {
                    try {
                        System.out.println(System.currentTimeMillis() + "，" + this.getName() + "获取到锁，开始等待");
                        object.wait();
                        System.out.println(System.currentTimeMillis() + "，" + this.getName() + "被唤醒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.setName("thread-1");
        t1.start();

        TimeUnit.SECONDS.sleep(1);
        synchronized (object){
            object.notify();
        }
    }
}
