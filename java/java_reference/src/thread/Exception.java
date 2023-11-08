package thread;

import java.util.concurrent.CompletableFuture;

public class Exception {
    public static void main(String[] args) throws java.lang.Exception {
        Integer age = -1;

        CompletableFuture<String> maturityFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync " + Thread.currentThread().getName());
            if(age < 0) {
                throw new IllegalArgumentException("Age can not be negative");
            }
            if(age > 18) {
                return "Adult";
            } else {
                return "Child";
            }
        }).exceptionally(ex -> {
            System.out.println("exceptionally " + Thread.currentThread().getName());
            System.out.println("Oops! We have an exception - " + ex.getMessage());
            return "Unknown!";
        });

        System.out.println("Maturity : " + maturityFuture.get());
    }
}