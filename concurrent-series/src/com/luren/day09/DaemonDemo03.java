package com.luren.day09;

import java.util.concurrent.TimeUnit;

/**
 * 线程 daemon 的默认值 为父线程的 daemon
 */
public class DaemonDemo03 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + ".daemon：" + Thread.currentThread().isDaemon());
        Trd t1 = new Trd();
        t1.setName("thread-1");
        t1.start();
        Thread t2 = new Thread(){
            @Override
            public void run() {
                System.out.println(this.getName() + ".daemon：" + this.isDaemon());
                Trd t3 = new Trd();
                t3.setName("thread-3");
                t3.start();
            }
        };
        t2.setName("thread-2");
        t2.setDaemon(true);
        t2.start();
        TimeUnit.SECONDS.sleep(2);
    }

    private static class Trd extends Thread {
        @Override
        public void run() {
            System.out.println(this.getName() + ".daemon：" + this.isDaemon());
        }
    }

}
