package com.luren.day18;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义创建线程的工厂
 * <p>
 * 给线程池中线程起一个有意义的名字，在系统出现问题的时候，
 * 通过线程堆栈信息可以更容易发现系统中问题所在。
 * 自定义创建工厂需要实现java.util.concurrent.ThreadFactory接口中的Thread newThread(Runnable r)方法，
 * 参数为传入的任务，需要返回一个工作线程。
 */
public class Demo04 {

    static AtomicInteger threadNum = new AtomicInteger();

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                5, 5, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("thread-" + threadNum.getAndIncrement());
                    return thread;
                }
        );

        for (int i = 0; i < 10; i++) {
            String name = "task-" + i;
            threadPoolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " 处理" + name);
            });
        }

        threadPoolExecutor.shutdown();
    }

}
