package ru.job4j.pool;

import ru.job4j.producerconsumer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private int size = Runtime.getRuntime().availableProcessors();

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(5);

    public void work(Runnable job) {
        try {
            this.tasks.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {

    }
}
