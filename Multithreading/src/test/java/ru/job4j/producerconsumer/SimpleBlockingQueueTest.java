package ru.job4j.producerconsumer;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);

    @Test
    public void test() throws InterruptedException {


        Thread producer = new Thread(
                () -> {
                    while (true) {
                        try {
                            this.queue.offer(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        );

        Thread consumer = new Thread(
                () -> {
                    while (true) {
                        try {
                            this.queue.poll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}