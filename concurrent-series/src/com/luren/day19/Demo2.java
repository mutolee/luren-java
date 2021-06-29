package com.luren.day19;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Lynn-God
 * @Description
 * @createTime 2021/6/29 13:48
 * @updateUser Lynn-God
 * @updateTime 2021/6/29 13:48
 * @desc
 */
public class Demo2 {

    /**
     * 从输出中可以看出，系统创建了50个线程处理任务，代码中使用了SynchronousQueue同步队列，<br>
     * 这种队列比较特殊，放入元素必须要有另外一个线程去获取这个元素，否则放入元素会失败或者一直阻塞在那里直到有线程取走，<br>
     * 示例中任务处理休眠了指定的时间，导致已创建的工作线程都忙于处理任务，所以新来任务之后，将任务丢入同步队列会失败，<br>
     * 丢入队列失败之后，会尝试新建线程处理任务。使用上面的方式创建线程池需要注意，<br>
     * 如果需要处理的任务比较耗时，会导致新来的任务都会创建新的线程进行处理，可能会导致创建非常多的线程，最终耗尽系统资源，触发OOM。
     */
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 50; i++) {
            int j = i;
            String taskName = "任务" + j;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "处理" + taskName);
                //模拟任务内部处理耗时
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }

}