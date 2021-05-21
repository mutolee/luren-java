package com.luren.day10;

public class Demo04 {

    // 临界资源
    int num = 0;

    public static void main(String[] args) throws InterruptedException {
        Demo04 demo02 = new Demo04();
        Trd t1 = new Trd(demo02);
        t1.start();
        Trd t2 = new Trd(demo02);
        t2.start();
        Trd t3 = new Trd(demo02);
        t3.start();

        // 等待三个线程结束
        t1.join();
        t2.join();
        t3.join();

        // 打印的结果 并不是 30000
        System.out.println(demo02.num);

    }


    private void m1(Demo04 demo04) {
        // 多个线程操作同一个实例，代码块 synchronized 锁的是 同一个实例
        synchronized (demo04){
            for (int i = 0; i < 10000; i++) {
                num++;
            }
        }
    }

    private static class Trd extends Thread {
        Demo04 demo04;

        private Trd(Demo04 demo04) {
            this.demo04 = demo04;
        }

        @Override
        public void run() {
            demo04.m1(demo04);
        }
    }
}
