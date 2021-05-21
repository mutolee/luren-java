package com.luren.day10;

public class Demo01 {

    // 临界资源
    static int num = 0;

    public static void main(String[] args) throws InterruptedException {
        Trd t1 = new Trd();
        t1.start();
        Trd t2 = new Trd();
        t2.start();
        Trd t3 = new Trd();
        t3.start();

        // 等待三个线程结束
        t1.join();
        t2.join();
        t3.join();

        // 打印的结果 并不是 30000
        System.out.println(num);

    }

    private static void m1(){
        for (int i = 0; i < 10000; i++) {
            num++;
        }
    }

    private static class Trd extends Thread {
        @Override
        public void run() {
            m1();
        }
    }
}
