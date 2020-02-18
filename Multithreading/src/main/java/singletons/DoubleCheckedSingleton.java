package singletons;

/**
 * Поле экземпляра класса обозначено volatile. Это решенит проблему видимости, после инициализации поля.
 * Первая проверка экземпляра идет до блока синхронизации, что позволяет увеличить скорость загрузки
 * по сравнению с single checked locking реализацией.
 * В критической секции происходит инициализация экземпляра класса и запись его в переменную.
 * Этот шаблон уменьшает производительность в многопоточном окргужении. Использовать этот шаблон не рекомендуется.
 */
public class DoubleCheckedSingleton {

    private static volatile DoubleCheckedSingleton instance;

    private DoubleCheckedSingleton() {
    }

    public static DoubleCheckedSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckedSingleton();
                }
            }
        }
        return instance;
    }

    public Object add(Object item) {
        return item;
    }

    public static void main(String[] args) {
        DoubleCheckedSingleton test = DoubleCheckedSingleton.getInstance();
    }
}
