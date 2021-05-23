package com.luren.day15;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore简单的使用
 * <p>
 * 限时获取信号量
 */
public class SemaphoreDemo05 {

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
    }

    static class Thrd extends Thread {
        @Override
        public void run() {
            boolean acquireSuccess = false;
            try {
                // 从信号量中申请一个许可证，限时 1 秒
                acquireSuccess = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if(acquireSuccess){
                    System.out.println(System.currentTimeMillis()
                            + "，" + this.getName() + " 获取一个许可证，当前信号量可用许可证：" + semaphore.availablePermits());
                    // 拿到许可证后休息 10 秒
                    TimeUnit.SECONDS.sleep(10);
                }else{
                    System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 没有获取到许可证");
                }
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 线程结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (acquireSuccess) {
                    semaphore.release();
                }
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 释放一个许可证，当前信号量可用许可证：" + semaphore.availablePermits());
            }
        }
    }

}
