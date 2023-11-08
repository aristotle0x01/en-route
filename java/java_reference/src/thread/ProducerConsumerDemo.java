package thread;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerDemo {
    public static void main(String[] args) throws InterruptedException {
        Demo demo = new Demo();
        Thread consumer1 = new Thread(() -> {
            while (true) {
                demo.consume();
            }
        }, "con1");
        Thread consumer2 = new Thread(() -> {
            while (true) {
                demo.consume();
            }
        }, "con2");
        Thread producer1 = new Thread(() -> {
            while (true) {
                demo.produce();
            }
        }, "pro1");
        Thread producer2 = new Thread(() -> {
            while (true) {
                demo.produce();
            }
        }, "pro2");
        consumer1.start();
        consumer2.start();
        producer1.start();
        producer2.start();

        System.out.println("main waiting:...");
        consumer1.join();
        consumer2.join();
        producer1.join();
        producer2.join();
        System.out.println("finished joining!");
    }

    static class Demo {
        private final int MAX_LEN = 20;
        private final int[] queue = new int[MAX_LEN];

        private int head = 0;
        private int tail = 0;

        private int count = 0;

        private void put(int v) {
            queue[head] = v;
            head = (head+1)%MAX_LEN;
            count++;
        }

        private int get() {
            int v = queue[tail];
            tail = (tail+1)%MAX_LEN;
            count--;

            return v;
        }

        private final Lock lock = new ReentrantLock();
        private final Condition notfull = lock.newCondition();
        private final Condition notempty = lock.newCondition();

        public void produce() {
            Random rand = new Random();

            lock.lock();
            try {
                while (count == MAX_LEN) {
                    try {
                        System.out.println(String.format("%s await", Thread.currentThread().getName()));
                        notfull.await();
                    } catch (java.lang.Exception e){}
                }

                int v = rand.nextInt();
                put(v);
                System.out.println(String.format("%s put %d", Thread.currentThread().getName(), v));
                notempty.signal();
            } catch (java.lang.Exception e) {}
            finally {
                lock.unlock();
            }
        }

        public void consume() {
            lock.lock();
            try {
                while (count == 0) {
                    try {
                        System.out.println(String.format("%s await", Thread.currentThread().getName()));
                        notempty.await();
                    } catch (java.lang.Exception e) {}
                }

                int v = get();
                System.out.println(String.format("%s get %d", Thread.currentThread().getName(), v));
                notfull.signal();
            }catch (java.lang.Exception e){}
            finally {
                lock.unlock();
            }
        }
    }
}
