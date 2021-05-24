package com.luren.day17;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 公司组织旅游，大家都有经历过，10个人，中午到饭点了，
 * 1 需要等到10个人都到了才能开饭，先到的人坐那等着；
 * 2 第 5 个人 只愿意等待 3 秒，不等了，开始吃，那么其他等待的人怎么办？看着他吃？
 *
 * 得出：第5个人抛出TimeoutException后，不等了，其他人及后来人都不等了
 *
 * 结论：
 *
 * 等待超时的方法
 *
 * public int await(long timeout, TimeUnit unit) throws InterruptedException,BrokenBarrierException,TimeoutException
 * 内部有一个人把规则破坏了（等待超时），其他人都不按规则来了，不会等待了
 *
 * 等待超时的线程，await方法会触发TimeoutException异常，然后被唤醒向下运行
 *
 * 其他等待中或者后面到达的线程，会在await()方法上触发`BrokenBarrierException`异常，然后继续执行
 *
 */
public class CyclicBarrierDemo05 {

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

        // 所有到期开始吃饭
        void eat() {
            long startTime = 0;
            try {
                // 模拟休眠
                TimeUnit.SECONDS.sleep(this.sleep);
                startTime = System.currentTimeMillis();
                System.out.println(startTime + "，" + this.getName() + "开始等待");
                if(this.getName().equals("thread-5")){
                    try {
                        cyclicBarrier.await(3,TimeUnit.SECONDS);
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    long endTime = System.currentTimeMillis();
                    System.out.println(this.getName() + "说，我等待了" + (endTime - startTime) + "，不等了，谁愿意等谁等！！！");
                }else{
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
