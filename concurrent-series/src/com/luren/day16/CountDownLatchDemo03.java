package com.luren.day16;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 使用 CountDownLatch 统计多个线程的工作耗时，限定等待各个线程的总时间
 * 在规定时间内，工作线程还没有结束，那么主线程不在继续等了
 */
public class CountDownLatchDemo03 {

    static CountDownLatch countDownLatch = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Thrd t1 = new Thrd("thread-1", 2);
        Thrd t2 = new Thrd("thread-2", 5);
        Thrd t3 = new Thrd("thread-3", 6);

        t1.start();
        t2.start();
        t3.start();

        boolean result = countDownLatch.await(3, TimeUnit.SECONDS);

        System.out.println("主线程等待时间：" + (System.currentTimeMillis() - startTime) + "，任务是否完成：" + result);
    }

    static class Thrd extends Thread {
        int sleepSeconds = 0;

        Thrd(String threadName, int sleepSeconds) {
            this.sleepSeconds = sleepSeconds;
            super.setName(threadName);
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            try {
                System.out.println(startTime + "，" + this.getName() + " 线程开始");
                TimeUnit.SECONDS.sleep(this.sleepSeconds);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime + "，" + this.getName() + " 线程结束，用时：" + (endTime - startTime));
        }
    }

}
