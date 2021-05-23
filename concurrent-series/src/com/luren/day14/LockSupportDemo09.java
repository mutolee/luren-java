package com.luren.day14;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 通过 LockSupport 阻塞、唤醒线程
 *
 * 模拟 线程中断
 *
 */
public class LockSupportDemo09 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " start");
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 中断前：" + this.isInterrupted());
                LockSupport.park();
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 中断后：" + this.isInterrupted());
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 被唤醒！");
            }
        };
        t1.setName("thread-1");
        t1.start();

        TimeUnit.SECONDS.sleep(5);
        t1.interrupt();
    }
}
