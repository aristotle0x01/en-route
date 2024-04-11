package thread.pool;

import java.util.concurrent.*;

public class CachedThreadPool {
    public static void main(String[] args) {
        newCachedThreadPool();
    }

    private static void newCachedThreadPool() {
        ExecutorService service = Executors.newCachedThreadPool();
        int numoftasks = 1000;
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
        service.shutdown();
        try {
            service.awaitTermination(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
