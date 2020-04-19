package ru.job4j.waitcontrol;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CountBarrierTest {

    @Test
    public void whenSecondThreadGetAccessThenResultIs3() throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(2);
        String[] result = new String[3];
        String[] expected = {"beforeBarrier", "beforeBarrier", "afterBarrier"};

        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 2; i++) {
                        countBarrier.count();
                        result[i] = "beforeBarrier";
                    }
                }
        );

        Thread second = new Thread(
                () -> {
                    countBarrier.await();
                    result[2] = "afterBarrier";
                }
        );

        first.start();
        first.join();
        second.start();
        second.join();

        assertThat(result, is(expected));
    }

}