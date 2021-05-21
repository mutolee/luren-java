package com.luren.day10;

public class Demo02 {

    // 临界资源
    int num = 0;

    public static void main(String[] args) throws InterruptedException {
        Demo02 demo02 = new Demo02();
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

    // 这个方法 加个 synchronized 修饰，可以保证数据的一致性
    // 多个线程操作同一个实例，synchronized 锁的是 当前实例
    private synchronized void m1() {
        for (int i = 0; i < 10000; i++) {
            num++;
        }
    }

    private static class Trd extends Thread {
        Demo02 demo02;

        private Trd(Demo02 demo02) {
            this.demo02 = demo02;
        }

        @Override
        public void run() {
            demo02.m1();
        }
    }
}
