package com.luren.day13;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition来实现一个阻塞队列的例子
 *
 * @param <T>
 */
public class BlockingQueueDemo08<T> {
    // 阻塞队列最大容量
    private int size;
    // 阻塞队列底层实现
    private LinkedList<T> queue = new LinkedList<>();

    public BlockingQueueDemo08(int size) {
        this.size = size;
    }

    private ReentrantLock lock = new ReentrantLock();

    // 队列满的时候，线程阻塞的条件
    private Condition full = lock.newCondition();
    // 队列空的时候，线程阻塞的条件
    private Condition empty = lock.newCondition();

    public void enqueue(T t) {
        lock.lock();
        while (queue.size() == size) {
            try {
                // 队列已满，在 full 条件上等着
                full.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            queue.add(t);
            System.out.println("入列：" + t);
            // 通知 empty 条件上的线程可以继续了
            empty.signal();
        } finally {
            lock.unlock();
        }
    }

    public T dequeue() {
        T t;
        lock.lock();
        while (queue.size() == 0) {
            try {
                empty.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            t = queue.removeFirst();
            System.out.println("出列：" + t);
            // 通知 full 条件上的线程可以继续了
            full.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BlockingQueueDemo08 blockingQueue = new BlockingQueueDemo08(2);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                blockingQueue.enqueue(finalI);
            }).start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                blockingQueue.dequeue();
            }).start();
        }
    }

}
