package ru.job4j.switcher;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class Switcher {

    private StringBuilder string = new StringBuilder();

    public void addToEnd(int input) {
        this.string.append(Integer.toString(input));
    }

    public String getString() {
        return string.toString();
    }

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        Switcher switcher = new Switcher();

        ReentrantLock locker = new ReentrantLock();

        Thread first = new Thread(
                () -> {
                    try {
                        locker.lock();
                        int counter = 0;
                        while (counter < 10) {
                            switcher.addToEnd(1);
                            counter++;
                        }
                    } finally {
                        locker.unlock();
                        latch.countDown();
                    }

                }
        );
        Thread second = new Thread(
                () -> {
                    try {
                        latch.await();
                        locker.lock();
                        int counter = 0;
                        while (counter < 10) {
                            switcher.addToEnd(2);
                            counter++;
                        }
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }finally {
                        locker.unlock();
                    }

                }
        );

        first.start();
        second.start();
        first.join();
        second.join();

        System.out.println(switcher.getString());
    }
}
