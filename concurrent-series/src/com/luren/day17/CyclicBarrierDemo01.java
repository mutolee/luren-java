package com.luren.day17;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 公司组织旅游，大家都有经历过，10个人，中午到饭点了，
 * 需要等到10个人都到了才能开饭，先到的人坐那等着，代码如下：
 */
public class CyclicBarrierDemo01 {

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

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

        @Override
        public void run() {
            try {
                // 模拟休眠
                TimeUnit.SECONDS.sleep(this.sleep);
                long startTime = System.currentTimeMillis();
                System.out.println(startTime + "，" + this.getName() + "开始等待");
                // 调用await()的时候，当前线程将会被阻塞，需要等待其他员工都到达await了才能继续
                cyclicBarrier.await();
                long endTime = System.currentTimeMillis();
                System.out.println(endTime + "，开饭" + this.getName() + "等待了" + (endTime - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

}
