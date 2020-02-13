package jmm;

public class Storage {

    private  Integer counter = 2;

    public static final Storage INSTANCE = new Storage();

    public static Storage getINSTANCE() {
        return INSTANCE;
    }

    private Storage() {
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
