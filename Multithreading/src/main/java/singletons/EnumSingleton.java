package singletons;

/**
 * Объект enum создается при загрузке класса и безопасно публикутся всем клиентам.
 */
public enum EnumSingleton {
    INSTANCE;

    public Object add(Object item) {
        return item;
    }

    public static void main(String[] args) {
        EnumSingleton test = EnumSingleton.INSTANCE;
    }
}
