package com.luren.day17;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 公司组织旅游，大家都有经历过，10个人，中午到饭点了，
 * 1 需要等到10个人都到了才能开饭，先到的人坐那等着；
 * 2 第 5 个人 只愿意等待 3 秒，不等了，开始吃，其他人等待的人及后来的人不干了，也开始吃
 * <p>
 * 3 15 秒后，导游说，你们太调皮了，重置规则，重新来
 */
public class CyclicBarrierDemo06 {

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
    static boolean guize = false;

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            Thrd thrd = new Thrd(i);
            thrd.setName("thread-" + i);
            thrd.start();
        }

        // 模拟 过了 15 秒后
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---------------大家太皮了，来了人就吃，也不等人了，现在规则重置------------------");
        // 来了人就吃，也不等人了，现在规则重置
        cyclicBarrier.reset();
        guize = true;
        for (int i = 1; i <= 10; i++) {
            Thrd thrd = new Thrd(i);
            thrd.setName("thread-" + i);
            thrd.start();
        }

    }

    static class Thrd extends Thread {
        long sleep = 0;

        Thrd(long sleep) {
            this.sleep = sleep;
        }

        // 所有到期开始吃饭
        void eat() {
            long startTime = 0;
            try {
                // 模拟休眠
                TimeUnit.SECONDS.sleep(this.sleep);
                startTime = System.currentTimeMillis();
                System.out.println(startTime + "，" + this.getName() + "开始等待");
                if (!guize) {
                    if (this.getName().equals("thread-5")) {
                        try {
                            cyclicBarrier.await(3, TimeUnit.SECONDS);
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        }
                        long endTime = System.currentTimeMillis();
                        System.out.println(this.getName() + "说，我等待了" + (endTime - startTime) + "，不等了，谁愿意等谁等！！！");
                    } else {
                        // 调用await()的时候，当前线程将会被阻塞，需要等待其他员工都到达await了才能继续
                        cyclicBarrier.await();
                    }
                } else {
                    // 调用await()的时候，当前线程将会被阻塞，需要等待其他员工都到达await了才能继续
                    cyclicBarrier.await();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime + "，开饭" + this.getName() + "等待了" + (endTime - startTime));
        }

        @Override
        public void run() {
            // 等待所有人到齐之后吃饭，先到的人坐那等着，什么事情不要干
            eat();
        }
    }

}
