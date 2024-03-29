package interview.yuaiweiwu.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SyncBySemaphore {
    public static void main(String[] args) {
        final Semaphore sem = new Semaphore(10);
        sem.drainPermits();

        final Object lock = new Object();

        List<Thread> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                System.out.printf("thread: %d in business\n", finalI);
                sem.release(1);
                System.out.printf("thread: %d released\n", finalI);

                if (sem.tryAcquire(10)) {
                    System.out.printf("thread: %d notifying\n", finalI);
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                } else {
                    System.out.printf(" **thread: %d waiting\n", finalI);
                    try {
                        synchronized (lock) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {}
                }
            });
            list.add(thread);
            thread.start();
        }

        try {
            synchronized (lock) {
                lock.wait();
            }
        } catch (InterruptedException e) {}
        System.out.println("main exiting!");
    }
}
