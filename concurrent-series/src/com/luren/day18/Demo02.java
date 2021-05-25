package com.luren.day18;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * SynchronousQueue队列的线程池
 */
public class Demo02 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 50; i++) {
            int j = i;
            String taskName = "task-" + j;
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " 处理" + taskName);
                // 模拟耗时操作
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }

}
