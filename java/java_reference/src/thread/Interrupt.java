package thread;

import java.util.concurrent.TimeUnit;

public class Interrupt {
    public static void main(String[] args) throws Throwable{
        Thread abnormal = new Thread(() -> {
            System.out.println("abnormal thread start");
            int i = 0;
            while (true) { i++;}
        }, "abnormal_thread");

        Thread normal = new Thread(() -> {
            System.out.println("normal thread start");
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) { i++;}
             System.out.println("normal thread end");
        }, "normal_thread");

        normal.start();
        TimeUnit.MILLISECONDS.sleep(100);
        normal.interrupt();
    }
}