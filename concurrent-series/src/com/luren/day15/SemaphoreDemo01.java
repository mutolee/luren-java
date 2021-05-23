package com.luren.day15;

import java.util.concurrent.Semaphore;

/**
 * Semaphore简单的使用
 */
public class SemaphoreDemo01 {

    static Semaphore semaphore = new Semaphore(2);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thrd t = new Thrd();
            t.setName("thread-" + (i + 1));
            t.start();
        }
    }

    static class Thrd extends Thread {
        @Override
        public void run() {
            try {
                // 从信号量中申请一个许可证
                semaphore.acquire();
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 获取一个许可证");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 释放一个许可证");
            }
        }
    }

}
