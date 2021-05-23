package com.luren.day14;

import java.util.concurrent.TimeUnit;

/**
 * 关于线程等待/唤醒的方法
 * <p>
 * 第一种，synchronized 中
 * wait() 和 notify() 都必须在 synchronized 中，否则程序将报错
 *
 * Exception in thread "thread-1" java.lang.IllegalMonitorStateException
 * 	at java.lang.Object.wait(Native Method)
 * 	at java.lang.Object.wait(Object.java:502)
 * 	at com.luren.day14.LockSupportDemo02$1.run(LockSupportDemo02.java:29)
 * Exception in thread "main" java.lang.IllegalMonitorStateException
 * 	at java.lang.Object.notify(Native Method)
 * 	at com.luren.day14.LockSupportDemo02.main(LockSupportDemo02.java:40)
 *
 */
public class ObjectLockDemo02 {
    static Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println(System.currentTimeMillis() + "，" + this.getName() + "获取到锁，开始等待");
                    object.wait();
                    System.out.println(System.currentTimeMillis() + "，" + this.getName() + "被唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.setName("thread-1");
        t1.start();

        TimeUnit.SECONDS.sleep(5);
        object.notify();
    }
}
