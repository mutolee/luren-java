package com.luren.day15;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore简单的使用
 *
 * 许可证不释放的情况
 *
 * 其他线程一直等待获取许可证，程序无法结束
 *
 */
public class SemaphoreDemo02 {

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
                TimeUnit.SECONDS.sleep(1);
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 运行结束");
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 当前可用许可证：" + semaphore.availablePermits());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

}
