package thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Compose {
    public static void main(String[] args) throws Throwable{
        {
            System.out.println("Retrieving weight.");
            CompletableFuture<Double> weightInKgFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                return 65.0;
            });

            System.out.println("Retrieving height.");
            CompletableFuture<Double> heightInCmFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                return 177.8;
            });

            System.out.println("Calculating BMI.");
            CompletableFuture<Double> combinedFuture = weightInKgFuture
                    .thenCombine(heightInCmFuture, (weightInKg, heightInCm) -> {
                        Double heightInMeter = heightInCm/100;
                        return weightInKg/(heightInMeter*heightInMeter);
                    });

            System.out.println("Your BMI is - " + combinedFuture.get());
        }

        {
            System.out.println(Thread.currentThread().getName());

            CompletableFuture<Double> result = getUsersDetail("ddddd")
                    .thenCompose(user -> getCreditRating());

            System.out.println(result.get());
        }
    }

    public static CompletableFuture<Double> getCreditRating() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("getCreditRating " + Thread.currentThread().getName());
            return 1.0;
        });
    }

    public static CompletableFuture<String> getUsersDetail(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("getUsersDetail " + Thread.currentThread().getName());
            return userId;
        });
    }
}