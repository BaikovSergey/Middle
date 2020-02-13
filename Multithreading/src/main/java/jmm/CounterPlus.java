package jmm;

public class CounterPlus implements Runnable {

    @Override
    public void run() {
        Storage storage = Storage.getINSTANCE();
        int counter = storage.getCounter() * 2;
        storage.setCounter(counter);
    }
}
