package lock;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerWithLockTest {

    static int staticValue = 0;

    public static void main(String[] args) throws InterruptedException {
        final int max = 10;
        final int loopCount = 100000;
        long costTime = 0;

        for (int i = 0; i < max; i++) {
            long start = System.nanoTime();
            final AtomicIntegerWithLock value1 = new AtomicIntegerWithLock(0);
            Thread[] ts = new Thread[max];
            for (int j = 0; j < max; j++) {
                ts[j] = new Thread(() -> {
                    for (int k = 0; k < loopCount; k++) {
                        value1.incrementAndGet();
                    }
                });
            }
            for (Thread t : ts) {
                t.start();
            }
            for (Thread t : ts) {
                t.join();
            }
            long end = System.nanoTime();
            costTime += (end - start);
        }
        System.out.println("cost1:" + costTime);

        costTime = 0;
        final Object lock = new Object();
        for (int i = 0; i < max; i++) {
            staticValue = 0;
            long start = System.nanoTime();
            final AtomicIntegerWithLock value1 = new AtomicIntegerWithLock(0);
            Thread[] ts = new Thread[max];
            for (int j = 0; j < max; j++) {
                ts[j] = new Thread(() -> {
                    for (int k = 0; k < loopCount; k++) {
                        synchronized (lock) {
                            ++staticValue;
                        }
                    }
                });
            }
            for (Thread t : ts) {
                t.start();
            }
            for (Thread t : ts) {
                t.join();
            }
            long end = System.nanoTime();
            costTime += (end - start);
        }
        System.out.println("cost2:" + costTime);

        for (int i = 0; i < max; i++) {
            long start = System.nanoTime();
            final AtomicInteger value1 = new AtomicInteger(0);
            Thread[] ts = new Thread[max];
            for (int j = 0; j < max; j++) {
                ts[j] = new Thread(() -> {
                    for (int k = 0; k < loopCount; k++) {
                        value1.incrementAndGet();
                    }
                });
            }
            for (Thread t : ts) {
                t.start();
            }
            for (Thread t : ts) {
                t.join();
            }
            long end = System.nanoTime();
            costTime += (end - start);
        }
        System.out.println("cost3:" + costTime);
    }


}
