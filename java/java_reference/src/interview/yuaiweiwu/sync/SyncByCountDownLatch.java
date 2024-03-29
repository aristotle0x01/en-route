package interview.yuaiweiwu.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SyncByCountDownLatch {
    public static void main(String[] args) {
        final CountDownLatch cdl = new CountDownLatch(10);

        List<Thread> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                System.out.printf("thread: %d countdown\n", finalI);
                cdl.countDown();
                try {
                    if (cdl.getCount() > 0) {
                        System.out.printf(" **thread: %d waiting\n", finalI);
                    }
                    cdl.await();
                } catch (InterruptedException e) {}
            });
            list.add(thread);
            thread.start();
        }

        while (true) {
            try {
                cdl.await();
                break;
            } catch (InterruptedException e) {}
        }
        System.out.println("main exiting!");
    }
}
