package com.luren.day16;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 一个并行处理任务的工具类
 */
public class TaskDisposeUtils {
    // 并行处理任务的线程数
    private static final int POOL_SIZE;

    static {
        POOL_SIZE = Integer.max(Runtime.getRuntime().availableProcessors(), 5);
    }


    /**
     * 并行处理，并等待结束
     *
     * @param taskList 任务列表
     * @param consumer 消费者
     * @param <T>
     */
    public static <T> void dispose(List<T> taskList, Consumer<T> consumer) {
        dispose(true, POOL_SIZE, taskList, consumer);
    }


    /**
     * 并行处理，并等待结束
     *
     * @param moreThread 是否多线程执行
     * @param poolSize   线程池大小
     * @param taskList   任务列表
     * @param consumer   消费者
     * @param <T>
     */
    public static <T> void dispose(boolean moreThread, int poolSize, List<T> taskList, Consumer<T> consumer) {
        if (taskList == null || taskList.size() == 0) {
            return;
        }
        if (moreThread && poolSize > 1) {
            poolSize = Math.min(poolSize, taskList.size());
            ExecutorService executorService = null;
            try {
                executorService = Executors.newFixedThreadPool(poolSize);
                CountDownLatch countDownLatch = new CountDownLatch(poolSize);
                for (T task : taskList) {
                    executorService.execute(() -> {
                        try {
                            consumer.accept(task);
                        } finally {
                            countDownLatch.countDown();
                        }
                    });
                }
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                if (executorService != null) {
                    executorService.shutdown();
                }
            }
        } else {
            for (T task : taskList) {
                consumer.accept(task);
            }
        }
    }

    public static void main(String[] args) {
        // 定义 10 个任务
        List<Integer> taskList = Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList());
        TaskDisposeUtils.dispose(taskList, (task) -> {
            System.out.println(Thread.currentThread().getName() + "开始工作，任务为" + task);
            try {
                TimeUnit.SECONDS.sleep(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "开始完成，任务为" + task);
        });
    }
}
