package sd.nosql.prototype.representation;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConcurrencyModel {
    private final ArrayBlockingQueue<QueueRequest> queue;
    private final AtomicBoolean isRunning;

    public ConcurrencyModel() {
        this.isRunning = new AtomicBoolean();
        this.queue = new ArrayBlockingQueue<>(100);
    }


    public ArrayBlockingQueue<QueueRequest> getQueue() {
        return queue;
    }

    public boolean getIsRunning() {
        return isRunning.getAcquire();
    }

    public void setIsRunning() {
        this.isRunning.getAndSet(true);
    }

    public void setIDLE() {
        this.isRunning.getAndSet(false);
    }

    public boolean isRunning() {
        return isRunning.getAcquire();
    }
}
