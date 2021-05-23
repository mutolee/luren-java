package com.luren.day14;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 通过 LockSupport 阻塞、唤醒线程
 *
 * 模拟 park(obj) 的意义
 *
 * jstack 中 可以看到 park(obj) 打印了 - parking to wait for  <0x000000076aed7370> (a com.luren.day14.LockSupportDemo10)
 * 方便跟踪查找问题，其他暂无用处
 *
 * "thread-2" #12 prio=5 os_prio=31 tid=0x000000012a84a800 nid=0x5803 waiting on condition [0x0000000172bbe000]
 *    java.lang.Thread.State: WAITING (parking)
 * 	at sun.misc.Unsafe.park(Native Method)
 * 	- parking to wait for  <0x000000076aed7370> (a com.luren.day14.LockSupportDemo10)
 * 	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
 * 	at com.luren.day14.LockSupportDemo10$2.run(LockSupportDemo10.java:29)
 *
 *    Locked ownable synchronizers:
 * 	- None
 *
 * "thread-1" #11 prio=5 os_prio=31 tid=0x000000012a84a000 nid=0xa403 waiting on condition [0x00000001729b2000]
 *    java.lang.Thread.State: WAITING (parking)
 * 	at sun.misc.Unsafe.park(Native Method)
 * 	at java.util.concurrent.locks.LockSupport.park(LockSupport.java:304)
 * 	at com.luren.day14.LockSupportDemo10$1.run(LockSupportDemo10.java:18)
 *
 */
public class LockSupportDemo10 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " start");
                LockSupport.park();
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 被唤醒！");
            }
        };
        t1.setName("thread-1");
        t1.start();

        Thread t2 = new Thread(){
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " start");
                LockSupport.park(new LockSupportDemo10());
                System.out.println(System.currentTimeMillis() + "，" + this.getName() + " 被唤醒！");
            }
        };
        t2.setName("thread-2");
        t2.start();

    }
}
