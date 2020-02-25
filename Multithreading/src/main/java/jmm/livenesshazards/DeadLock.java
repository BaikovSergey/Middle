package jmm.livenesshazards;

/**
 * Пример угрозы живучести. Исполнение такого кода ведет к взаимной блокировке (deadlock).
 * Такая ситуация решается сменой порядка получения блокировки для каждого из двух объектов.
 * В данном примере:
 * public void leftRight()  {
 *      synchronized (left) {
 *          synchronized (right) {
 *
 *          }
 *      }
 * }
 * public void leftRight()  {
 *      synchronized (right) {
 *          synchronized (left) {
 *
 *          }
 *     }
 * }
 * Такой порядок ведет в к взаимной блокировке (dead lock).
 * Решается данная ситуация путем изменения порядка получения блокировки в одном из методов.
 */
public class DeadLock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight()  {
        synchronized (left) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (right) {
                System.out.println(left.hashCode());
            }
        }
    }

    public void rightLeft() {
        synchronized (right) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (left) {
                System.out.println(right.hashCode());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLock test = new DeadLock();
        Thread first = new Thread(
                test::leftRight
        );
        Thread second = new Thread(
                test::rightLeft
        );

        first.join();
        second.join();
        first.start();
        second.start();
    }
}
