package com.luren.day18;

import java.util.concurrent.*;

/**
 * 线程池使用的简单示例
 */
public class Demo01 {

    static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            3, 5, 10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int j = i;
            String taskName = "task-" + j;
            executor.execute(() -> {
                try {
                    // 模拟任务内部处理耗时
                    TimeUnit.SECONDS.sleep(j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " " + taskName + "处理完成");
            });
        }
        // 关闭线程池
        executor.shutdown();
    }
}
