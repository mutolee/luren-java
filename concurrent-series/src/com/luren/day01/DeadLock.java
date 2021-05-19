package com.luren.day01;

/**
 * 死锁的例子
 */
public class DeadLock {

    public static void main(String[] args) {
        Obj1 obj1 = new Obj1();
        Obj2 obj2 = new Obj2();
        Thread thread1 = new Thread(new SynAddRunalbe(obj1, obj2, true));
        thread1.setName("thread-1");
        thread1.start();
        Thread thread2 = new Thread(new SynAddRunalbe(obj1, obj2, false));
        thread2.setName("thread-2");
        thread2.start();
    }

    /**
     * 线程死锁等待演示
     */
    public static class SynAddRunalbe implements Runnable {
        Obj1 obj1;
        Obj2 obj2;
        boolean flag;

        public SynAddRunalbe(Obj1 obj1, Obj2 obj2, boolean flag) {
            this.obj1 = obj1;
            this.obj2 = obj2;
            this.flag = flag;
        }


        /**
         * "thread-2":
         *   waiting to lock monitor 0x000000014f80e160 (object 0x000000076ac28a60, a com.luren.day01.Deadlock$Obj1),
         *   which is held by "thread-1"
         * "thread-1":
         *   waiting to lock monitor 0x000000014f80ccc0 (object 0x000000076ac2b998, a com.luren.day01.Deadlock$Obj2),
         *   which is held by "thread-2"
         */
        @Override
        public void run() {
            try {
                if (flag) {
                    synchronized (obj1) {
                        Thread.sleep(100);
                        synchronized (obj2) {
                            System.out.println(Thread.currentThread().getName());
                        }
                    }
                } else {
                    synchronized (obj2) {
                        Thread.sleep(100);
                        synchronized (obj1) {
                            System.out.println(Thread.currentThread().getName());
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static class Obj1 {
    }

    public static class Obj2 {
    }
}
