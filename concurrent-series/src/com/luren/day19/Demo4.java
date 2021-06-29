package com.luren.day19;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Lynn-God
 * @Description
 * @createTime 2021/6/29 14:47
 * @updateUser Lynn-God
 * @updateTime 2021/6/29 14:47
 * @desc
 */
public class Demo4 {

    static AtomicInteger threadNum = new AtomicInteger(1);

    /**
     * 自定义线程创建工厂，可以设置线程池中的线程名称
     */
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10), r -> {
            Thread thread = new Thread(r);
            thread.setName("自定义线程-" + threadNum.getAndIncrement());
            return thread;
        });
        for (int i = 0; i < 5; i++) {
            String taskName = "任务-" + i;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "处理" + taskName);
            });
        }
        executor.shutdown();
    }

}