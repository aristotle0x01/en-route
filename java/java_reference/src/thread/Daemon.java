package thread;

import java.util.concurrent.TimeUnit;

public class Daemon {
    public static void main(String[] args) throws Throwable{
        Thread daemon = new Thread(() -> {
            System.out.println("daemon thread start");
            while (true) {
                System.gc();
                System.out.println("gc called by daemon!");
            }
        }, "daemon_thread");
        daemon.setDaemon(true);

        daemon.start();
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("main exit");
        // daemon or not, join will wait for thread exit
        // daemon.join();
    }
}
