package interview.yuaiweiwu.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SyncByCondition {
    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
        final Condition cond = lock.newCondition();
        final int MAX = 10;
        final AtomicInteger count = new AtomicInteger();

        List<Thread> list = new ArrayList<>();
        for (int i=0; i<MAX; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                System.out.printf("thread: %d in business\n", finalI);

                try {
                    lock.lock();
                    int v = count.incrementAndGet();
                    if (v == MAX) {
                        System.out.printf("thread: %d notifying\n", finalI);
                        cond.signalAll();
                    } else {
                        while (count.intValue() != MAX) {
                            System.out.printf(" **thread: %d waiting\n", finalI);
                            cond.awaitUninterruptibly();
                        }
                    }
                } finally {
                    lock.unlock();
                }
            });
            list.add(thread);
            thread.start();
        }

        try {
            lock.lock();
            while (!count.compareAndSet(MAX, MAX)) {
                System.out.println(" **main thread waiting\n");
                cond.awaitUninterruptibly();
            }
        } finally {
            lock.unlock();
        }
        System.out.println("main exiting!");
    }
}
