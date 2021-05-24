package com.luren.day17;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 公司组织旅游，大家都有经历过，10个人，中午到饭点了，
 * 1 需要等到10个人都到了才能开饭，先到的人坐那等着；
 * 2 第 5 个人 等待过程中，接了个电话有事，不等了，开始吃，那么其他等待的人怎么办？看着他吃？
 *
 * 输出的信息看着有点乱，给大家理一理，员工5遇到急事，
 * 拿起筷子就是吃，这样好么，当然不好，他这么做了，
 * 后面看他这么做了都跟着这么做（这种场景是不是很熟悉，有一个人拿起筷子先吃起来，其他人都跟着上了），
 * 直接不等其他人了，拿起筷子就开吃了。
 *
 * 注意 cyclicBarrier 为 11，第10个人 wait() 不会继续等11个人，直接BrokenBarrierException 然后继续
 *
 *
 * 结论：
 *
 * 内部有一个人把规则破坏了（接收到中断信号），其他人都不按规则来了，不会等待了
 * 接收到中断信号的线程，await方法会触发InterruptedException异常，然后被唤醒向下运行
 * 其他等待中 或者后面到达的线程，会在await()方法上触发`BrokenBarrierException`异常，然后继续执行
 *
 */
public class CyclicBarrierDemo04 {

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(11);

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            int sleep = 0;
            if(i == 10){
                sleep = 10;
            }

            Thrd thrd = new Thrd(sleep);
            thrd.setName("thread-" + i);
            thrd.start();

            // 模拟第 5 个人，接了电话，不等了，用中断线程的方式结束等待
            if(i == 5){
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(thrd.getName() + ",有点急事，我先开干了！");
                    thrd.interrupt();
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
                // 调用await()的时候，当前线程将会被阻塞，需要等待其他员工都到达await了才能继续
                cyclicBarrier.await();
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
