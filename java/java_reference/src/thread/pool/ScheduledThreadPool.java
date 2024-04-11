package thread.pool;

import java.util.concurrent.*;

public class ScheduledThreadPool {
    public static void main(String[] args) {
        newScheduledThreadPool();
        newScheduledThreadPool2();
    }

    private static void newScheduledThreadPool2() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
        int numoftasks = 10;
        Future<?>[] futures = new Future[numoftasks];
        for (int i=0; i<1; i++) {
            final int ii = i;
            ScheduledFuture<?> future = service.scheduleAtFixedRate(() -> {
                System.out.println(Thread.currentThread().getName());
                long sum = 0;
                int j = 0;
                while (j < (ii+1) * 100000) {
                    sum += j;
                    j++;
                }
                return;
            }, ii, 5, TimeUnit.SECONDS);
            futures[i] = future;
        }

        try {
            service.awaitTermination(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (int i=0; i<1; i++) {
                System.out.println(futures[i].cancel(true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
        try {
            service.awaitTermination(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void newScheduledThreadPool() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
        int numoftasks = 10;
        Future<?>[] futures = new Future[numoftasks];
        for (int i=0; i<numoftasks; i++) {
            final int ii = i;
            ScheduledFuture<?> future = service.schedule((Callable<Object>) () -> {
                System.out.println(Thread.currentThread().getName());
                long sum = 0;
                int j = 0;
                while (j < (ii+1) * 100000) {
                    sum += j;
                    j++;
                }
                return sum;
            }, ii, TimeUnit.SECONDS);
            futures[i] = future;
        }

        try {
            for (int i=0; i<numoftasks; i++) {
                System.out.println(futures[i].get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdown();
        try {
            service.awaitTermination(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
