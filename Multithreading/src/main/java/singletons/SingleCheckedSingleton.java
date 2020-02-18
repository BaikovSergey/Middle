package singletons;

/**
 * Ленивая загрузка. Инициализация и проверка экземпляра происходит в кретической секции.
 * Этот шаблон уменьшает производительность в многопоточном окргужении. Использовать этот шаблон не рекомендуется.
 */
public class SingleCheckedSingleton {

    private static SingleCheckedSingleton instance;

    private SingleCheckedSingleton() {
    }

    public static synchronized SingleCheckedSingleton getInstance() {
        if (instance == null) {
            instance = new SingleCheckedSingleton();
        }
        return instance;
    }

    public Object add(Object item) {
        return item;
    }

    public static void main(String[] args) {
        SingleCheckedSingleton test = SingleCheckedSingleton.getInstance();
    }
}
