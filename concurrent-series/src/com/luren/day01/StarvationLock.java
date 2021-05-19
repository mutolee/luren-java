package com.luren.day01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 饥饿死锁的例子
 *
 * 主线程执行 submit_01.get() 的时候，
 * 由于线程池 single 是一个线程，submit_02.get() 得不到执行，被饿死，导致程序死锁。
 *
 */
public class StarvationLock {

    static ExecutorService single = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws Exception {
        Callable_01 callable_01 = new Callable_01();
        Future<String> submit_01 = single.submit(callable_01);
        System.out.println(submit_01.get());
        single.shutdown();
    }

    public static class Callable_01 implements Callable<String>{
        @Override
        public String call() throws Exception {
            Callable_02 callable_02 = new Callable_02();
            Future<String> submit_02 = single.submit(callable_02);
            System.out.println(submit_02.get());
            return "callable_01 success";
        }
    }

    public static class Callable_02 implements Callable<String>{
        @Override
        public String call() throws Exception {
            return "callable_02 success";
        }
    }
}
