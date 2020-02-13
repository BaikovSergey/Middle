package jmm;

public class RaceCondition  {
    public static void main(String[] args) {
        Thread threadOne = new Thread(new CounterPlus());
        Thread threadTwo = new Thread(new CounterMinus());
        Storage storage = Storage.getINSTANCE();
        threadOne.start();
        threadTwo.start();
        System.out.println(storage.getCounter());


    }
}
