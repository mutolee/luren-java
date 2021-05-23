package com.luren.day14;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 通过 LockSupport 阻塞、唤醒线程
 *
 * 模拟 先唤醒再阻塞
 *
 */
public class LockSupportDemo08 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " start");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LockSupport.park();
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 被唤醒！");
            }
        };
        t1.setName("thread-1");
        t1.start();

        TimeUnit.SECONDS.sleep(1);
        LockSupport.unpark(t1);

        System.out.println(System.currentTimeMillis() + "，" + t1.getName() + " 执行完毕！");
    }
}
