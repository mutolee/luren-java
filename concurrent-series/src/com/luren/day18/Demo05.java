package com.luren.day18;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池 自定义饱和策略
 *
 * 输出结果中可以看到有3个任务进入了饱和策略中，
 * 记录了任务的日志，对于无法处理多任务，我们最好能够记录一下，让开发人员能够知道。
 * 任务进入了饱和策略，说明线程池的配置可能不是太合理，或者机器的性能有限，需要做一些优化调整。
 *
 */
public class Demo05 {

    static class Task implements Runnable {
        String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 处理" + this.name);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Task{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 1, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                Executors.defaultThreadFactory(),
                (r, executor) -> {
                    // 自定义饱和策略
                    // 记录一下无法处理的任务
                    System.out.println("无法处理任务：" + r.toString());
                }
        );

        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.execute(new Task("task-" + i));
        }
        threadPoolExecutor.shutdown();
    }
}
