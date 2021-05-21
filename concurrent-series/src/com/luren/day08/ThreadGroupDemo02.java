package com.luren.day08;

import java.util.concurrent.TimeUnit;

public class ThreadGroupDemo02 {
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup threadGroup01 = new ThreadGroup("ThreadGroup-1");
        Thread t1 = new Thread(threadGroup01, new R1(), "t1");
        Thread t2 = new Thread(threadGroup01, new R1(), "t2");
        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("threadGroup01活动线程数：" + threadGroup01.activeCount());
        System.out.println("threadGroup01活动线程组：" + threadGroup01.activeGroupCount());
        System.out.println("threadGroup01线程组名称：" + threadGroup01.getName());
        System.out.println("threadGroup01线程组父线程组名称：" + threadGroup01.getParent().getName());
        System.out.println("----------------------------");
        ThreadGroup threadGroup02 = new ThreadGroup(threadGroup01, "ThreadGroup-2");
        Thread t3 = new Thread(threadGroup02, new R1(), "t3");
        Thread t4 = new Thread(threadGroup02, new R1(), "t4");
        t3.start();
        t4.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("threadGroup02活动线程数：" + threadGroup02.activeCount());
        System.out.println("threadGroup02活动线程组：" + threadGroup02.activeGroupCount());
        System.out.println("threadGroup02线程组名称：" + threadGroup02.getName());
        System.out.println("----------------------------");
        System.out.println("threadGroup01活动线程数：" + threadGroup01.activeCount());
        System.out.println("threadGroup01活动线程组：" + threadGroup01.activeGroupCount());
        System.out.println("----------------------------");
        threadGroup01.list();
    }


    private static class R1 implements Runnable {
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            System.out.println("所属线程组：" + thread.getThreadGroup().getName() + "，线程名称：" + thread.getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
