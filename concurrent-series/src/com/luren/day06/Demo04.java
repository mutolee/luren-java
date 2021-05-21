package com.luren.day06;

import java.util.concurrent.TimeUnit;

public class Demo04 {

    /**
     * 中断线程例子
     */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!Thread.interrupted()){ // 判断线程中断状态，同时清除中断标识
                    ;
                }
                System.out.println("线程中断状态：" + this.isInterrupted());
                System.out.println("线程退出");
            }
        };
        thread.setName("thread-1");
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();
    }
}
