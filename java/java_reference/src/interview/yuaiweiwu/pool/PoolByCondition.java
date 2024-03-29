package interview.yuaiweiwu.pool;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PoolByCondition {
    private Queue<MyObject> pool;

    private final int initLen;

    private final ReentrantLock lock;
    private final Condition cond;

    private final ObjectFactory<MyObject> factory;

    public PoolByCondition(ObjectFactory<MyObject> factory, int initsize) {
        if (initsize <= 0) {
            throw new InvalidParameterException("initsize <= 0");
        }

        this.factory = factory;

        this.initLen = initsize;
        this.pool = new LinkedList<>();
        for (int i=0; i<this.initLen; i++) {
            this.pool.offer(this.factory.createObject());
        }

        lock = new ReentrantLock();
        cond = lock.newCondition();
    }

    public void giveback(MyObject o) {
        lock.lock();
        try {
            this.pool.offer(o);
            cond.signal();
        }finally {
            lock.unlock();
        }
    }

    // <=0: 不等待
    public MyObject take(int seconds) {
        lock.lock();
        try {
            if (seconds <= 0) {
                return pool.poll();
            }

            long left = TimeUnit.SECONDS.toNanos(seconds);
            while (this.pool.isEmpty() && left > 0) {
                try {
                    left = cond.awaitNanos(left);
                } catch (InterruptedException e){}
            }

            return pool.poll();
        }finally {
            lock.unlock();
        }
    }
}
