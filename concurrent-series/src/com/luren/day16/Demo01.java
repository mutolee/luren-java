package com.luren.day16;

import java.util.concurrent.TimeUnit;

/**
 * 使用 join 统计多个线程的工作耗时
 */
public class Demo01 {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Thrd t1 = new Thrd("thread-1", 2);
        Thrd t2 = new Thrd("thread-2", 5);
        Thrd t3 = new Thrd("thread-3", 6);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("总耗时：" + (System.currentTimeMillis() - startTime));
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime + "，" + this.getName() + " 线程结束，用时：" + (endTime - startTime));
        }
    }
}
