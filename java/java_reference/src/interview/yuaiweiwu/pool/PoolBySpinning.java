package interview.yuaiweiwu.pool;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class PoolBySpinning {
    private Queue<MyObject> pool;

    private final int initLen;
    private final AtomicBoolean processing;

    private final ObjectFactory<MyObject> factory;

    public PoolBySpinning(ObjectFactory<MyObject> factory, int initsize) {
        if (initsize <= 0) {
            throw new InvalidParameterException("initsize <= 0");
        }
        this.processing = new AtomicBoolean(false);
        this.factory = factory;
        this.initLen = initsize;
        this.pool = new LinkedList<>();
        for (int i=0; i<this.initLen; i++) {
            this.pool.offer(this.factory.createObject());
        }
    }

    public void giveback(MyObject o) {
        while (!processing.compareAndSet(false, true)){
            this.pool.offer(o);
            processing.set(false);
            break;
        }
    }

    // <=0: 不等待
    public MyObject take(int seconds) {
        if (seconds <= 0) {
            return pool.poll();
        }

        long start = System.currentTimeMillis() % 1000;
        while (!processing.compareAndSet(false, true)) {
            if (!pool.isEmpty()) {
                MyObject r = pool.poll();
                processing.set(false);
                return r;
            } else {
                processing.set(false);

                long current = System.currentTimeMillis() % 1000;
                if ((current-start) >= seconds*1000L) {
                    return null;
                }
            }
        }

        return pool.poll();
    }
}
