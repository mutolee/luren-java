package com.luren.day09;

/**
 * 守护线程是一种特殊的线程，在后台默默地完成一些系统性的服务，
 * 比如垃圾回收线程、JIT线程都是守护线程。
 * 与之对应的是用户线程，用户线程可以理解为是系统的工作线程，它会完成这个程序需要完成的业务操作。
 * 如果用户线程全部结束了，意味着程序需要完成的业务操作已经结束了，系统可以退出了。
 * 所以当系统只剩下守护进程的时候，java虚拟机会自动退出。
 */
public class DaemonDemo01 {

    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("我是" + (this.isDaemon() ? "守护线程" : "用户线程"));
                while (true) ;
            }
        };
        thread.start();
        // 因为 thread 是工作线程，所以主线程一直不会结束
        // 下面这行打印之后，程序一直还在运行中
        System.out.println("主线程结束");
    }

}
