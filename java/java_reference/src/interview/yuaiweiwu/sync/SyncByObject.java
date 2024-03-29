package interview.yuaiweiwu.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SyncByObject {
    public static void main(String[] args) {
        Object obj = new Object();
        AtomicInteger countdown = new AtomicInteger(10);

        List<Thread> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                synchronized (obj) {
                    int tmp = countdown.getAndDecrement();
                    System.out.printf("thread: %d dec value %d\n", finalI, tmp);
                    if (tmp == 1) {
                        obj.notifyAll();
                    }
                    while (countdown.get() > 0) {
                        System.out.printf(" **thread: %d waiting\n", finalI);
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {}
                    }
                }
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
