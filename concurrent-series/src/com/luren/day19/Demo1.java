package com.luren.day19;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Lynn-God
 * @Description
 * @createTime 2021/6/29 13:24
 * @updateUser Lynn-God
 * @updateTime 2021/6/29 13:24
 * @desc
 */
public class Demo1 {

    /**
     * corePoolSize：核心线程大小，当提交一个任务到线程池时，线程池会创建一个线程来执行任务，
     * 即使有其他空闲线程可以处理任务也会创新线程，等到工作的线程数大于核心线程数时就不会在创建了。
     * 如果调用了线程池的prestartAllCoreThreads方法，线程池会提前把核心线程都创造好，并启动
     * <p>
     * maximumPoolSize：线程池允许创建的最大线程数。如果队列满了，并且以创建的线程数小于最大线程数，则线程池会再创建新的线程执行任务。
     * 如果我们使用了无界队列，那么所有的任务会加入队列，这个参数就没有什么效果了
     * <p>
     * keepAliveTime：线程池的工作线程空闲后，保持存活的时间。
     * 如果没有任务处理了，有些线程会空闲，空闲的时间超过了这个值，会被回收掉。
     * 如果任务很多，并且每个任务的执行时间比较短，避免线程重复创建和回收，可以调大这个时间，提高线程的利用率
     * <p>
     * unit：keepAliveTIme的时间单位，可以选择的单位有天、小时、分钟、毫秒、微妙、千分之一毫秒和纳秒。
     * 类型是一个枚举java.util.concurrent.TimeUnit，这个枚举也经常使用，有兴趣的可以看一下其源码
     * <p>
     * workQueue：工作队列，用于缓存待处理任务的阻塞队列
     * <p>
     * threadFactory：线程池中创建线程的工厂，可以通过线程工厂给每个创建出来的线程设置更有意义的名字
     * <p>
     * handler：饱和策略，当线程池无法处理新来的任务了，那么需要提供一种策略处理提交的新任务
     *
     * <p>
     * <br>
     * <b>调用线程池的execute方法处理任务，执行execute方法的过程：</b><br><br>
     * 1. 判断线程池中运行的线程数是否小于corepoolsize，是：则创建新的线程来处理任务，否：执行下一步<br>
     * 2. 试图将任务添加到workQueue指定的队列中，如果无法添加到队列，进入下一步<br>
     * 3. 判断线程池中运行的线程数是否小于maximumPoolSize，是：则新增线程处理当前传入的任务，否：将任务传递给handler对象rejectedExecution方法处理
     *
     */
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(3,5,10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            int j = i;
            String taskName = "任务-" + j;
            executor.execute(()->{
                try {
                    // 模拟内部耗时
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + taskName + "处理完毕");
            });
        }
        executor.shutdown();
    }

}