package com.luren.day10;

/**
 * synchronized 同步 所持有 锁 的范围
 */
public class Demo05 {

    // 作用于当前类的同一个实例对象
    public synchronized void m1() {
    }

    // 作用于当前类的同一个实例对象
    public void m2() {
        synchronized (this) {

        }
    }

    // 作用于当前类Class对象
    public synchronized static void m3() {
    }

    // 作用于当前类Class对象
    public static void m4() {
        synchronized (Demo05.class) {

        }
    }

}
