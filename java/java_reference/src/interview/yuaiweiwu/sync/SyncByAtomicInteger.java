package interview.yuaiweiwu.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SyncByAtomicInteger {
    public static void main(String[] args) {
        AtomicInteger ai = new AtomicInteger(10);

        List<Thread> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                int v = ai.getAndDecrement();
                while (!ai.compareAndSet(0, 0)) {
                    System.out.printf(" **thread: %d waiting\n", finalI);
                }
                System.out.printf("thread: %d dec value %d\n", finalI, v);
            });
            list.add(thread);
            thread.start();
        }

        for (Thread t: list) {
            try {
                t.join();
            }catch (InterruptedException ex) {}
        }
        System.out.println("main exiting!");
    }
}
