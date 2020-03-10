package ru.job4j.jmm.livenesshazards;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Пример угрозы живучести. Исполнение такого кода ведет к "голоду" одной из нитей (thread5).
 * В данном примере созданным нитям был присвоин приоритет, тем самым орпеделив их очередь.
 * Нити thread5 был присвоин самый низкий приоритет. В этом случае нить thread5 будет исполняться одной из последних.
 * В реальных программах крайне нежелательно использовать приоритеты нитий так, как это превращает программу в
 * платформозависимою (выставляемые приоритеты это "подсказки" для ОС о сових приоритетах. В разных ОС они разные).
 * Для решеня проглемы "голодания" нужно корректно проектировать многопоточную систему.
 */
public class Starvation extends Thread {

    static volatile AtomicInteger threadcount = new AtomicInteger(1);
    public void run() {
        System.out.println(threadcount + "st Child"
                + " Thread execution starts");
        System.out.println(threadcount + "st Child thread execution completes");
        threadcount.incrementAndGet();
    }
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main thread execution starts");
        Starvation thread1 = new Starvation();
        thread1.setPriority(10);
        Starvation thread2 = new Starvation();
        thread2.setPriority(8);
        Starvation thread3 = new Starvation();
        thread1.setPriority(6);
        Starvation thread4 = new Starvation();
        thread1.setPriority(4);
        Starvation thread5 = new Starvation();
        thread1.setPriority(2);

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        Thread.sleep(3000);

        System.out.println("Main thread execution completes");
    }
}
