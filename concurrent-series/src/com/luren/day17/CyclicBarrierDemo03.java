package com.luren.day17;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 公司组织旅游，大家都有经历过，10个人，中午到饭点了，
 * 1 需要等到10个人都到了才能开饭，先到的人坐那等着；
 * 2 最后到的人，给大家倒酒赔罪
 */
public class CyclicBarrierDemo03 {

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
        // 模拟倒酒，用时 2 秒
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println(Thread.currentThread().getName() + "说：'不好意啊，大家久等了，先干为敬！'");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });

    public static void main(String[] args) {
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
            try {
                // 模拟休眠
                TimeUnit.SECONDS.sleep(this.sleep);
                long startTime = System.currentTimeMillis();
                System.out.println(startTime + "，" + this.getName() + "开始等待");
                // 调用await()的时候，当前线程将会被阻塞，需要等待其他员工都到达await了才能继续
                cyclicBarrier.await();
                long endTime = System.currentTimeMillis();
                System.out.println(endTime + "，开饭" + this.getName() + "等待了" + (endTime - startTime));

                // 模拟吃饭耗时
                TimeUnit.SECONDS.sleep(this.sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            // 等待所有人到齐之后吃饭，先到的人坐那等着，什么事情不要干
            eat();
        }
    }

}
