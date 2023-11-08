package thread;

import java.util.concurrent.CompletableFuture;

public class Sync {
    public static void main(String[] args) throws Throwable{
        {
            Thread t = new Thread(){
                @Override
                public void run() {
//                    int j = 0;
//                    for(int i=0;i<1000*1000*10;i++){
//                        j = j + i;
//                    }
//
//                    System.out.println("ddd " + j);
                    try {
                        Thread.sleep(1000*30);
                    }catch (InterruptedException e){
                        System.out.println(e.toString());
                    }
                }
            };
            t.start();

            t.interrupt();

            int i = 0;
        }

        System.out.println(Thread.currentThread().getName() + ": in main");;

        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + ": " + completableFuture.get());
                }catch (Throwable e){
                    System.out.println(e.toString());
                }
            }
        };
        t.start();
        completableFuture.complete("Future's Result");

        Thread.sleep(1000);
    }
}