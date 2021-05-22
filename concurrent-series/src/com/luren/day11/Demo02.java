package com.luren.day11;

import java.util.concurrent.TimeUnit;

/**
 * 线程中断例子
 */
public class Demo02 {

    public static void main(String[] args) throws InterruptedException {
        Thrd thrd = new Thrd();
        thrd.start();
        TimeUnit.SECONDS.sleep(3);
        thrd.interrupt();
    }


    private static class Thrd extends Thread {
        @Override
        public void run() {
            while (this.isInterrupted()) {
                break;
            }
        }
    }
}
