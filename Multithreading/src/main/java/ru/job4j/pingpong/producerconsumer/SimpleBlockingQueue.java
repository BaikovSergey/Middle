package ru.job4j.pingpong.producerconsumer;

import javax.annotation.concurrent.ThreadSafe;
import javax.annotation.concurrent.GuardedBy;
import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private int maxSize;

    public SimpleBlockingQueue(int maxLimit) {
        this.maxSize = maxLimit;
    }

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) {
        this.queue.offer(value);
    }

    public synchronized T poll() throws InterruptedException {
        T result = null;
        if (this.queue.isEmpty()) {
            Thread.currentThread().wait();
        } else {
            result = this.queue.poll();
        }
        return result;
    }
}
