package jmm.perfomancehazards;

/**
 * Пример кода с проблемой видимости. В данном случае есить две нити - reader и writer. Reader пытается считать
 * значение переменной counter, а writer увеличивает её значение на 1. Если оставить все как есть то reader сможет
 * прочитать значение переменной counter только один раз (при изменение её значения со сзначения по умолчанию на 1).
 * Это происходит потому, что значение переменной counter для каждой нити свои (они хранятся в кэше L1 ядер).
 * Исправить это можно введя ключевое слово volatile (static volatile int counter). Это позволит хранить значение
 * переменной counter в общей памяти (RAM).
 */
public class VisibilityProblem {

    static int counter;

    public static void main(String[] args) {
        Thread reader = new Thread(
                () -> {
                    int temp = 0;
                    while (true) {
                        if (temp != counter) {
                            temp = counter;
                            System.out.println("reader: value of counter = " + counter);
                        }
                    }
                }
        );

        Thread writer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        counter++;
                        System.out.println("writer: changed value counter = " + counter);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
        );
        reader.start();
        writer.start();
    }
}
