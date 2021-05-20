package com.luren.day06;

import java.util.concurrent.TimeUnit;

public class Demo03 {

    /**
     * 中断线程例子
     */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // thread-1 休息 100 秒
                        TimeUnit.SECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // 注意：sleep方法由于中断而抛出异常捕获之后，线程的中断标志会被清除（置为false），
                        // 所以在异常中需要执行this.interrupt()方法，将中断标志位置为true
                        this.interrupt();
                    }
                    if(this.isInterrupted()){
                        System.out.println("线程要退出了...");
                        break;
                    }
                }
            }
        };
        thread.setName("thread-1");
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();
    }
}
