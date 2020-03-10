package ru.job4j.singletons;


public class FinalFieldSingleton {

    private static final FinalFieldSingleton INSTANCE = new FinalFieldSingleton();

    private FinalFieldSingleton() {
    }

    public static FinalFieldSingleton getInstance() {
        return INSTANCE;
    }

    public Object add(Object item) {
        return item;
    }

    public static void main(String[] args) {
        FinalFieldSingleton test = FinalFieldSingleton.getInstance();
    }
}
