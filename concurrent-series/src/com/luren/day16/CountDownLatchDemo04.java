package com.luren.day16;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 两个 CountDownLatch 配合使用的例子
 *
 * 例子介绍：
 *
 * 三名选手跑步比赛，三名选手准备后，指令员开枪，开始比赛
 *
 */
public class CountDownLatchDemo04 {

    // 指令员
    static CountDownLatch commander = new CountDownLatch(1);
    // 三名选手
    static CountDownLatch countDownLatch = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {

        Thrd t1 = new Thrd("thread-1", 2);
        Thrd t2 = new Thrd("thread-2", 5);
        Thrd t3 = new Thrd("thread-3", 6);

        t1.start();
        t2.start();
        t3.start();

        // 主线程 3 秒后 枪响
        TimeUnit.SECONDS.sleep(3);
        commander.countDown();

        long startTime = System.currentTimeMillis();
        countDownLatch.await();
        System.out.println("主线程等待时间：" + (System.currentTimeMillis() - startTime));
    }

    static class Thrd extends Thread {
        int sleepSeconds = 0;

        Thrd(String threadName, int sleepSeconds) {
            this.sleepSeconds = sleepSeconds;
            super.setName(threadName);
        }

        @Override
        public void run() {
            long startTime = 0;
            try {
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 选手准备就绪");
                commander.await();
                startTime = System.currentTimeMillis();
                System.out.println(startTime + "，" + this.getName() + " 听到枪响了，开始跑");
                TimeUnit.SECONDS.sleep(this.sleepSeconds);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime + "，" + this.getName() + " 选手，用时：" + (endTime - startTime));
        }
    }

}
