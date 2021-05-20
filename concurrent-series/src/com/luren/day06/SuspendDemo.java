package com.luren.day06;

import java.util.concurrent.TimeUnit;

/**
 * 挂起（suspend）和继续执行（resume）线程
 * 被挂起的线程，必须要等到resume()方法操作后，才能继续执行
 * 如果 resume 被提前执行，线程将无法结束
 * 系统中已经标注着2个方法过时了，不推荐使用。
 */
public class SuspendDemo {
    static Object object = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thrd t1 = new Thrd();
        t1.setName("thread-1");
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        Thrd t2 = new Thrd();
        t2.setName("thread-2");
        t2.start();
        t1.resume();
        t2.resume(); // 这里 早于了 suspend 执行，所以 thread-2 无法结束
    }

    private static class Thrd extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println(this.getName());
                this.suspend();
            }
        }
    }
}
