package com.luren.day08;

import java.util.concurrent.TimeUnit;

public class ThreadGroupDemo01 {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup threadGroup = new ThreadGroup("ThreadGroup-1");
        Thread t1 = new Thread(threadGroup, new R1(), "t1");
        Thread t2 = new Thread(threadGroup, new R1(), "t2");
        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("活动线程数：" + threadGroup.activeCount());
        System.out.println("活动线程组：" + threadGroup.activeGroupCount());
        System.out.println("线程组名称：" + threadGroup.getName());
    }

    private static class R1 implements Runnable {
        @Override
        public void run() {
            System.out.println("ThreadName：" + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
