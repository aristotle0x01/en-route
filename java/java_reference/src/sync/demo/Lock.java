package sync.demo;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Lock {
    public static void main(String[] args) throws Throwable{
        ReentrantLock rl = new ReentrantLock();
        System.out.println("new ReentrantLock: ");
        System.out.println(ClassLayout.parseInstance(rl).toPrintable());
        rl.lock();
        System.out.println("    after locking: ");
        System.out.println(ClassLayout.parseInstance(rl).toPrintable());
        try {
            System.out.println("    critical running：");
        } finally {
            rl.unlock();
            System.out.println("    release locking: ");
            System.out.println(ClassLayout.parseInstance(rl).toPrintable());
        }

        System.out.println("--------------------");
        System.out.println("--------------------");

        Semaphore sema = new Semaphore(1);
        System.out.println("new Semaphore: ");
        System.out.println(ClassLayout.parseInstance(sema).toPrintable());
        sema.acquire();
        System.out.println("    after locking: ");
        System.out.println(ClassLayout.parseInstance(sema).toPrintable());
        try {
            System.out.println("    critical running：");
        } finally {
            sema.release();
            System.out.println("    release locking: ");
            System.out.println(ClassLayout.parseInstance(sema).toPrintable());
        }

        System.out.println("--------------------");
        System.out.println("--------------------");

        // 睡眠 5s
        Thread.sleep(5000);
        Object o = new Object();
        System.out.println("未进入同步块，MarkWord 为：");
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o){
            System.out.println(("进入同步块，MarkWord 为："));
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }




    }
}