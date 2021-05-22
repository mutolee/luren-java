package com.luren.day12;

/**
 * 使用 synchronized 对临界资源进行操作 的例子
 */
public class Demo01 {
    static int num = 0;

    public static void main(String[] args) throws InterruptedException {
        Thrd t1 = new Thrd();
        t1.start();
        Thrd t2 = new Thrd();
        t2.start();
        Thrd t3 = new Thrd();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(num);
    }

    static synchronized void add() {
        for (int i = 0; i < 10000; i++) {
            num++;
        }
    }

    static class Thrd extends Thread {
        @Override
        public void run() {
            add();
        }
    }
}
