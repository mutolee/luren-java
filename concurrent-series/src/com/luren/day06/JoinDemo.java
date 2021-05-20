package com.luren.day06;

import java.util.concurrent.TimeUnit;

/**
 * 等待线程结束（join）和谦让（yeild）
 *
 * yield是谦让的意思，这是一个静态方法，一旦执行，它会让当前线程出让CPU，
 * 但需要注意的是，出让CPU并不是说不让当前线程执行了，当前线程在出让CPU后，还会进行CPU资源的争夺，但是能否再抢到CPU的执行权就不一定了。
 */
public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("时间：" + System.currentTimeMillis() + " 主线程开始");
        Thrd thread = new Thrd();
        thread.setName("thread-1");
        thread.start();
        thread.join(); // 等待子线程 thread-1 结束
        System.out.println("时间：" + System.currentTimeMillis() + " 主线程结束");
    }

    private static class Thrd extends Thread {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
