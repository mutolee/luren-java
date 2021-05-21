package com.luren.day08;

import java.util.concurrent.TimeUnit;

public class ThreadGroupDemo04 {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup threadGroup01 = new ThreadGroup("ThreadGroup-1");
        Thread t1 = new Thread(threadGroup01, new R1(), "t1");
        Thread t2 = new Thread(threadGroup01, new R1(), "t2");
        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        ThreadGroup threadGroup02 = new ThreadGroup(threadGroup01, "ThreadGroup-2");
        Thread t3 = new Thread(threadGroup02, new R1(), "t3");
        Thread t4 = new Thread(threadGroup02, new R1(), "t4");
        t3.start();
        t4.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("------------线程组 ThreadGroup-1 信息----------------");
        threadGroup01.list();
        System.out.println("------------停止线程组 ThreadGroup-1 所有子孙线程----------------");
        threadGroup01.interrupt();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("------------线程组 ThreadGroup-1 停止所有线程之后的信息----------------");
        threadGroup01.list();
    }


    private static class R1 implements Runnable {
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            System.out.println("所属线程组：" + thread.getThreadGroup().getName() + "，线程名称：" + thread.getName());
            while (!thread.isInterrupted()) {
                ;
            }
            System.out.println("线程" + thread.getName() + "中断了");
        }
    }
}
