package ru.job4j.pool;

import ru.job4j.producerconsumer.SimpleBlockingQueue;

import java.util.*;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadPool {

    private volatile boolean interrupt = false;

    private final int maxthreads;

    private final List<Thread> threads = new LinkedList<>();

    private final Queue<Thread> threadsQueue = new SynchronousQueue<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(2);

    public ThreadPool(int size) {
        this.maxthreads = size;
    }

    public void work(Runnable job) {
        if (!this.interrupt) {
            try {
                this.tasks.offer(job);
                run();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    private void run() {
            try {
                Thread thread = new Thread(this.tasks.poll());
                this.threads.add(thread);
                thread.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
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
