package thread.pool;

import java.util.concurrent.*;

public class FixedThreadPool {
    public static void main(String[] args) {
        newFixedThreadPool();
    }

    private static void newFixedThreadPool() {
        ExecutorService service = Executors.newFixedThreadPool(3);
        int numoftasks = 10;
        Future<?>[] futures = new Future[numoftasks];
        for (int i=0; i<numoftasks; i++) {
            final int ii = i;
            Future<?> future = service.submit((Callable<Object>) () -> {
                System.out.println(Thread.currentThread().getName());
                long sum = 0;
                int j = 0;
                while (j < (ii+1) * 100000) {
                    sum += j;
                    j++;
                }
                return sum;
            });
            futures[i] = future;
        }

        try {
            for (int i=0; i<numoftasks; i++) {
                System.out.println(futures[i].get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.shutdownNow();
        System.out.println("******: " + service.isTerminated());

        try {
            service.awaitTermination(10, TimeUnit.SECONDS);
            service.submit((Callable<Object>) () -> {
                System.out.println("after shutdown " + Thread.currentThread().getName());
                long sum = 0;
                int j = 0;
                while (j < 100000) {
                    sum += j;
                    j++;
                }
                return sum;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("******: " + service.isTerminated());
    }
}
