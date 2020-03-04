package nonblockingcache;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.is;

public class NonBlockingCacheTest {

    NonBlockingCache cache = new NonBlockingCache();

    @Test
    public void whenThrowException() throws InterruptedException {
        AtomicReference<Exception> ex = new AtomicReference<>();
        Thread thread = new Thread(
                () -> {
                    try {
                        throw new RuntimeException("Throw Exception in Thread");
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        thread.start();
        thread.join();
        Assert.assertThat(ex.get().getMessage(), is("Throw Exception in Thread"));
    }

    @Test
    public void whenAddModelThenThisModelInCache() throws InterruptedException {
        Base model = new Base(1);
        Thread thread = new Thread(
                () -> this.cache.add(model)
        );
        thread.start();
        thread.join();
        Assert.assertThat(this.cache.modelExist(model), is(true));
    }

    @Test
    public void whenUpdateModelWithNoExceptionThenUpdateModel() throws InterruptedException {
        Base model = new Base(1);
        AtomicBoolean result = new AtomicBoolean(false);
        Thread first = new Thread(
                () -> this.cache.add(model)
        );
        Thread second = new Thread(
                () -> result.set(this.cache.update(model))

        );
        first.start();
        second.start();
        first.join();
        second.join();
        Assert.assertThat(result.get(), is(true));
    }

    @Test
    public void whenUpdateModelWithExceptionThenException() throws InterruptedException {
        Base initial = new Base(1);
        Base modified = new Base(1);
        modified.setVersion(1);
        AtomicReference<Exception> ex = new AtomicReference<>();
        AtomicBoolean result = new AtomicBoolean(false);
        Thread second = new Thread(
                () -> {
                    try {
                        this.cache.add(modified);
                        result.set(this.cache.update(initial));
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        second.start();
        second.join();
        Assert.assertThat(result.get(), is(false));
        Assert.assertThat(ex.get().getMessage(), is("Model was modified"));
    }

}