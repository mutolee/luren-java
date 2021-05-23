package com.luren.day15;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore简单的使用
 * <p>
 * 没有正确释放许可证的情况
 * 可以看到当程序运行结束的时候，信号量可用许可证变成了 3 ，而我们设置的为 1
 */
public class SemaphoreDemo03 {

    static Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {
        Thrd t1 = new Thrd();
        t1.setName("thread-1");
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        Thrd t2 = new Thrd();
        t2.setName("thread-2");
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        Thrd t3 = new Thrd();
        t3.setName("thread-3");
        t3.start();

        // t2 t3 发送中断信息
        t2.interrupt();
        t3.interrupt();
    }

    static class Thrd extends Thread {
        @Override
        public void run() {
            try {
                // 从信号量中申请一个许可证
                semaphore.acquire();
                System.out.println(System.currentTimeMillis()
                        + "，" + this.getName() + " 获取一个许可证，当前信号量可用许可证：" + semaphore.availablePermits());
                // 拿到许可证后休息 10 秒
                TimeUnit.SECONDS.sleep(10);
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 休息结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 释放一个许可证，当前信号量可用许可证：" + semaphore.availablePermits());
            }
        }
    }

}
