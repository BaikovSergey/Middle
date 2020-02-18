package threadpool;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    int size = Runtime.getRuntime().availableProcessors();

    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    public void work(Runnable job) {

    }

    public void shutdown() {

    }
}
