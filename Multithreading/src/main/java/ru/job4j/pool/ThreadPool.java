package ru.job4j.pool;

import ru.job4j.producerconsumer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private boolean interrupt = false;

    private int size = Runtime.getRuntime().availableProcessors();

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(5);

    public void work(Runnable job) {
        if (!this.interrupt) {
            try {
                this.tasks.offer(job);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public Runnable run() {
        Runnable result = null;
        if (!this.tasks.isEmpty()) {
            try {
                result = this.tasks.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void shutdown() {
        this.interrupt = true;
    }
}
