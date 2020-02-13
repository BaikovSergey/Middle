package jmm;

public class Worker {

    private Worker() {

    }

    private final static Worker INSTANCE = new Worker();

    public static Worker getINSTANCE() {
        return INSTANCE;
    }

    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public void inc() {
        this.counter++;
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = Worker.getINSTANCE();
        Thread thread = new Thread(new Observer());
        thread.start();
        while (true) {
            worker.inc();
            Thread.sleep(1000);
        }
    }
}
