package thread.pool;

import java.util.concurrent.*;

public class SingleThreadExecutor {
    public static void main(String[] args) {
        newSingleThreadExecutor();
    }

    private static void newSingleThreadExecutor() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<?> future = service.submit((Callable<Object>) () -> {
            int i = 0;
            int sum = 0;
            while (i < 100000) {
                sum += i;
                i++;
            }
            return sum;
        });
        try {
            System.out.println(future.get());
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
