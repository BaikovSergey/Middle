package synch;

import ru.job4j.list.DinamicArray;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final DinamicArray<T> list = new DinamicArray<>(10);

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return this.list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.list).iterator();
    }

    private DinamicArray<T> copy(DinamicArray<T> list) {
        DinamicArray<T> result = new DinamicArray<>(10);
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }
}
