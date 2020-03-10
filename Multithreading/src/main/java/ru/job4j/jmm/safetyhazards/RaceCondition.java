package jmm.safetyhazards;

/**
 * Прирмер угрозы безопасности. Такой код позволяет двум и более нитям получить несинхронизированный
 * доступ к общему ресурсу (value).
 * В данной ситуации мы получаем "race condition" и не можем гарантировать корректность результата работы метода.
 * Такая ситуация решается вводом синхронизации. В результате чего в один момент времени только одня нить получает
 * доступ к ресурсу (value).
 */
public class RaceCondition {
    private int value;

    public int getNext() {
        return this.value++;
    }

    public static void main(String[] args) throws InterruptedException {
        RaceCondition test = new RaceCondition();
        Thread first = new Thread(
                () -> System.out.println(test.getNext())
        );
        Thread second = new Thread(
                () -> System.out.println(test.getNext())
        );
        first.join();
        second.join();
        first.start();
        second.start();
    }
}
