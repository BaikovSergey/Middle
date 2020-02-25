package jmm.livenesshazards;

/**
 * Пример угрозы живучести. Исполнение такого кода ведет к блокировки (livelock).
 * В данном примере грабитель и полицейский ждут друг от друга выполнения своих условий.
 * Такая ситуация решается введением случайности в поведение системы.
 * Например: при возникновении блокировки (livelock) обоим потокам необходимо остановиться на случайное кол-во времени.
 */
public class LiveLock {

    public class Criminal {

        private boolean hostageReleased = false;

        public void releaseHostage(Police police) {
            while (!police.isMoneySent()) {

                System.out.println("Criminal: waiting police to give ransom");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            System.out.println("Criminal: released hostage");

            this.hostageReleased = true;
        }

        public boolean isHostageReleased() {
            return this.hostageReleased;
        }
    }

    public class Police {
        private boolean moneySent = false;

        public void giveRansom(Criminal criminal) {

            while (!criminal.isHostageReleased()) {

                System.out.println("Police: waiting criminal to release hostage");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            System.out.println("Police: sent money");

            this.moneySent = true;
        }

        public boolean isMoneySent() {
            return this.moneySent;
        }
    }

    public void example() {
        final Police police = new Police();
        final Criminal criminal = new Criminal();
        Thread t1 = new Thread(() -> police.giveRansom(criminal));
        t1.start();

        Thread t2 = new Thread(() -> criminal.releaseHostage(police));
        t2.start();
    }

    public static void main(String[] args) {
         LiveLock test = new LiveLock();
         test.example();
    }
}
