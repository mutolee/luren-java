package com.luren.day11;

import java.util.concurrent.TimeUnit;

/**
 * 阻塞状态下的线程中断例子
 */
public class Demo03 {
    public static void main(String[] args) throws InterruptedException {
        Thrd thrd = new Thrd();
        thrd.start();
        TimeUnit.SECONDS.sleep(3);
        thrd.interrupt();
    }

    private static class Thrd extends Thread{
        @Override
        public void run() {
            while (!this.isInterrupted()){
                System.out.println("线程运行中，开始阻塞...");
                try {
                    // 这里线程 休息 1000 秒，模拟阻塞
                    TimeUnit.SECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 这里捕获到中断异常之后，中断标识会被清除，线程又变成了不是中断状态了
                    // 所以 这里需要在调用一次 中断
                    this.interrupt();
                    if(this.isInterrupted()){
                        break;
                    }
                }
            }
        }
    }

}
