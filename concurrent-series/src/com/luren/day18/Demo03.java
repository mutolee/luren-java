package com.luren.day18;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * PriorityBlockingQueue优先级队列的线程池
 *
 * 输出中，除了第一个任务，其他任务按照优先级高低按顺序处理。
 * 原因在于：创建线程池的时候使用了优先级队列，进入队列中的任务会进行排序，任务的先后顺序由Task中的i变量决定。
 * 向PriorityBlockingQueue加入元素的时候，内部会调用代码中Task的compareTo方法决定元素的先后顺序。
 *
 */
public class Demo03 {
    static class Task implements Runnable, Comparable<Task> {

        private int i;
        private String name;

        public Task(int i, String name) {
            this.i = i;
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 处理" + this.name);
        }

        @Override
        public int compareTo(Task o) {
            return Integer.compare(o.i, this.i);
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(
                1, 1, 60L, TimeUnit.SECONDS,
                new PriorityBlockingQueue<>()
        );
        for (int i = 0; i < 10; i++) {
            String name = "Task-" + i;
            executorService.execute(new Task(i, name));
        }
        for (int i = 100; i >= 90; i--) {
            String name = "Task-" + i;
            executorService.execute(new Task(i, name));
        }
        executorService.shutdown();
    }

}
