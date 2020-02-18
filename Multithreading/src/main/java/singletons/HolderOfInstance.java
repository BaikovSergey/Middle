package singletons;

/**
 * Такая реализация стабильна и не влияет на производительность системы.
 *
 * Вывод.
 * Если нет необходимиости в ленийвой загрузке - используйте enum реализацию. Например при инициализации Кеша или БД.
 * Если в приложение есть затратные ресурсы нужно импользовать реализацию с ленивой загрузкой. Здесь можно использовать
 * только один шаблон - Holder. Другие реализации будут негативно влиять на производительность.
 *
 */
public class HolderOfInstance {

    private HolderOfInstance() {
    }

    public static HolderOfInstance getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final HolderOfInstance INSTANCE = new HolderOfInstance();
    }

    public Object add(Object item) {
        return item;
    }

    public static void main(String[] args) {
        HolderOfInstance test = HolderOfInstance.getInstance();
    }
}
