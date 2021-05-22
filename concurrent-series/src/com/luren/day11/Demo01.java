package com.luren.day11;

import java.util.concurrent.TimeUnit;

/**
 * 线程中断例子
 */
public class Demo01 {
    static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        Thrd thrd = new Thrd();
        thrd.start();
        TimeUnit.SECONDS.sleep(3);
        exit();
    }

    private static void exit() {
        flag = false;
    }

    private static class Thrd extends Thread {
        @Override
        public void run() {
            while (flag) {
                break;
            }
        }
    }
}
