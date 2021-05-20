package com.luren.day06;

import java.util.concurrent.TimeUnit;

public class Demo01 {

    /**
     * 终止线程例子
     */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("start");
                boolean flag = true;
                while (flag) {
                    ;
                }
                System.out.println("end");
            }
        };
        thread.setName("thread-1");
        thread.start();
        // 主线程休息 1 秒
        TimeUnit.SECONDS.sleep(1);
        // 输出线程 thread-1 的状态
        System.out.println(thread.getState());
        // 关闭线程 thread-1
        // stop方法为何会被废弃而不推荐使用？stop方法过于暴力，强制把正在执行的方法停止了。
        thread.stop();
        // 主线程休息 1 秒
        TimeUnit.SECONDS.sleep(1);
        // 输出线程 thread-1 的状态
        System.out.println(thread.getState());
    }
}
