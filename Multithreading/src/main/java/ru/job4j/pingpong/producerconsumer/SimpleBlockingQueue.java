package ru.job4j.pingpong.producerconsumer;

import javax.annotation.concurrent.ThreadSafe;
import javax.annotation.concurrent.GuardedBy;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private volatile int capacity;

    public SimpleBlockingQueue(int maxLimit) {
        this.capacity = maxLimit;
    }

    private final Queue<T> queue = new LinkedList<>();

    public void offer(T value) throws InterruptedException {
        synchronized (this.queue) {
            while (this.queue.size() == this.capacity) {
                System.out.println("Producer" + Thread.currentThread().getName() + " is waiting...");
                System.out.println(this.queue.size());
                this.queue.wait(ThreadLocalRandom.current().nextInt(1000));
                System.out.println("Producer" + Thread.currentThread().getName() + " is awakened");
            }
            this.queue.offer(value);
            this.queue.notifyAll();
            System.out.println("Offering item");
            Thread.sleep(1000);
        }
    }

    public T poll() throws InterruptedException {
        T result;
        synchronized (this.queue) {
            while (this.queue.size() == 0) {
                System.out.println("Consumer " + Thread.currentThread().getName() + " is empty, waiting...");
                System.out.println(this.queue.size());
                this.queue.wait(ThreadLocalRandom.current().nextInt(1000));
                System.out.println("Consumer " + Thread.currentThread().getName() + " is awakened");
            }
            result = this.queue.poll();
            this.queue.notifyAll();
            System.out.println("Item was taken");
            Thread.sleep(1000);
            return result;
        }
    }
}
