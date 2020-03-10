package ru.job4j.producerconsumer;

import javax.annotation.concurrent.ThreadSafe;
import javax.annotation.concurrent.GuardedBy;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private final int capacity;

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (this.queue.size() == this.capacity) {
            wait(ThreadLocalRandom.current().nextInt(1000));
        }
        this.queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        T result;
        while (this.queue.size() == 0) {
            wait(ThreadLocalRandom.current().nextInt(1000));
        }
        result = this.queue.poll();
        notifyAll();
        return result;
    }
}
