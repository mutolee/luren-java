package com.luren.day14;

import java.util.concurrent.TimeUnit;

/**
 * 关于线程等待/唤醒的方法
 * <p>
 * 第一种，synchronized 中 使用 wait\notify
 *
 */
public class ObjectLockDemo01 {
    static Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
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

        TimeUnit.SECONDS.sleep(5);
        synchronized (object){
            object.notify();
        }
    }
}
