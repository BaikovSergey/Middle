package jmm;

public class Observer implements Runnable {

    Worker worker = Worker.getINSTANCE();

    @Override
    public void run() {
        while (true) {
            System.out.println(worker.getCounter());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
