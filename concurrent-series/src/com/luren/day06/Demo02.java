package com.luren.day06;

import java.util.concurrent.TimeUnit;

public class Demo02 {

    static volatile boolean isStop = false;

    /**
     * 中断线程例子
     */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (true){
                    if(isStop){
                        System.out.println("线程要退出了...");
                        break;
                    }
                }
            }
        };
        thread.setName("thread-1");
        thread.start();
        TimeUnit.SECONDS.sleep(2);
        isStop = true;
    }
}
