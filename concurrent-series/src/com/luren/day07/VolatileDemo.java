package com.luren.day07;

import java.util.concurrent.TimeUnit;

/**
 * volatile与Java内存模型
 *
 * static boolean flag = true;
 * 主线程把 flag 设置为 false ,子线程无法直接获取到
 * 使用 volatile 修饰 flag ,子线程每次使用 flag 都从主内存中获取，然后复制到自己工作内存中，就可以及时获取到主线程修改的值了
 */
public class VolatileDemo {

    // static boolean flag = true; // 这样写，子线程无法获取到

    // 这样写，主线程修改了 flag 会及时从工作内存刷新到主内存中
    // 其他线程每次使用 flag ，也会从主内存复制到工作内存再使用
    static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        Thrd thrd = new Thrd();
        thrd.start();
        // 主线程休息 3 秒
        TimeUnit.SECONDS.sleep(3);
        // 将 flag 设置 false
        flag = false;
    }

    private static class Thrd extends Thread{
        @Override
        public void run() {
            System.out.println("线程开始");
            while (flag){
                ;
            }
            System.out.println("线程结束");
        }
    }
}
