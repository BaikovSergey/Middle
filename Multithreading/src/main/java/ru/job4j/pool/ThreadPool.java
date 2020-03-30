package ru.job4j.pool;

import ru.job4j.producerconsumer.SimpleBlockingQueue;
import java.util.*;


public class ThreadPool {

    private volatile boolean interrupt = false;

    private boolean firstRun = true;

    private final int maxThreads;

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(10);

    public ThreadPool(int threadLimit) {
        this.maxThreads = threadLimit;
    }

    public void work(Runnable job) {
        if (!this.interrupt) {
            try {
                this.tasks.offer(job);
                if (firstRun) {
                   init();
                   firstRun = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    private void init() {
        for (int i = 0; i < maxThreads; i++) {
            Runnable runnable = () -> {
                try {
                    while (!this.tasks.isEmpty()) {
                        this.tasks.poll().run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            };
            Thread thread = new Thread(runnable);
            this.threads.add(thread);
            thread.start();
        }
    }

    public void shutdown() {
        this.interrupt = true;
    }

    public static void main(String[] args) {
        ThreadPool pool = new ThreadPool(2);

        Runnable r1 = new Task("task 1");
        Runnable r2 = new Task("task 2");
        Runnable r3 = new Task("task 3");
        Runnable r4 = new Task("task 4");
        Runnable r5 = new Task("task 5");

        pool.work(r1);
        pool.work(r2);
        pool.work(r3);
        pool.work(r4);
        pool.work(r5);

        pool.shutdown();

    }
}
