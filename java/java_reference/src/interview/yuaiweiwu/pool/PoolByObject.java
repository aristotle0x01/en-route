package interview.yuaiweiwu.pool;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.Queue;

public class PoolByObject {
    private Queue<MyObject> pool;

    private final int initLen;

    // serve as lock
    private final Object lockObj;

    private final ObjectFactory<MyObject> factory;

    public PoolByObject(ObjectFactory<MyObject> factory, int initsize) {
        if (initsize <= 0) {
            throw new InvalidParameterException("initsize <= 0");
        }

        lockObj = new Object();

        this.factory = factory;
        this.initLen = initsize;
        this.pool = new LinkedList<>();
        for (int i=0; i<this.initLen; i++) {
            this.pool.offer(this.factory.createObject());
        }
    }

    public void giveback(MyObject o) {
        synchronized (lockObj) {
            this.pool.offer(o);
            lockObj.notify();
        }
    }

    // <=0: 不等待
    public MyObject take(int seconds) {
        synchronized (lockObj) {
            if (seconds <= 0) {
                return pool.poll();
            }

            while (this.pool.isEmpty()) {
                try {
                    lockObj.wait(seconds);
                    break;
                } catch (InterruptedException e){}
            }

            return pool.poll();
        }
    }
}
