package thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class RunASync {
    public static void main(String[] args) throws Throwable{
        {
            CompletableFuture<String> welcomeText = CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                return "Rajeev";
            }).thenApply(name -> {
                return "Hello " + name;
            }).thenApply(greeting -> {
                return greeting + ", Welcome to the CalliCoder Blog";
            });

            System.out.println(welcomeText.get());
        }

        {
            // Create a CompletableFuture
            CompletableFuture<String> whatsYourNameFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    System.out.println(Thread.currentThread().getName());
                    //TimeUnit.SECONDS.sleep(1);
                } catch (Throwable e) {
                    throw new IllegalStateException(e);
                }
                return "Rajeev";
            });

            // Attach a callback to the Future using thenApply()
            CompletableFuture<String> greetingFuture = whatsYourNameFuture.thenApply(name -> {
                System.out.println("thenApply " + Thread.currentThread().getName());
                return "Hello " + name;
            });

            // Block and get the result of the future.
            System.out.println(greetingFuture.get()); // Hello Rajeev
        }

        System.out.println(Thread.currentThread().getName() + ": in main");

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("I'll run in a separate thread than the main thread.");
        });

        future.get();

        // Using Lambda Expression
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of the asynchronous computation";
        });
        String result = future1.get();
        System.out.println(result);
    }
}