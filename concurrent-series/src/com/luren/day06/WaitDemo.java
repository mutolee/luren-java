package com.luren.day06;

import java.util.concurrent.TimeUnit;

/**
 * 等待（wait）和通知（notify）
 */
public class WaitDemo {
    static Object object = new Object();
    public static void main(String[] args) {
        Thread thread01 = new Thread(){
            @Override
            public void run() {
                synchronized (object){
                    try {
                        System.out.println("thread01 ：" + System.currentTimeMillis());
                        // wait() 之后，线程开始等待，object 锁会释放
                        object.wait();
                        // 被唤醒之后，需要重新抢到 cpu 才会执行
                        System.out.println("thread01 ：" + System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread01.start();

        Thread thread02 = new Thread(){
            @Override
            public void run() {
                synchronized (object){
                    // notify() 之后，唤醒 object 上所有等待的线程进行 cpu 争夺
                    object.notify();
                    System.out.println("thread02 ：" + System.currentTimeMillis());
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread02.start();
    }
}
