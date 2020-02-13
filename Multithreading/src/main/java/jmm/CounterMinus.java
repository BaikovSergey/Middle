package jmm;

public class CounterMinus implements Runnable {
    @Override
    public void run() {
        Storage storage = Storage.getINSTANCE();
        int counter = storage.getCounter() * 3;
        storage.setCounter(counter);
    }
}
