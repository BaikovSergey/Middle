package ru.job4j.waitcontrol;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CountBarrierTest {

    @Test
    public void whenSecondThreadGetAccessThenResultIs3() throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(2);
        List<String> result = new ArrayList<>();
        List<String> expected = List.of("beforeBarrier", "beforeBarrier", "afterBarrier");

        Thread first = new Thread(
                () -> {
                    for (int i = 0; i < 2; i++) {
                        countBarrier.count();
                        result.add("beforeBarrier");
                    }
                }
        );

        Thread second = new Thread(
                () -> {
                    countBarrier.await();
                    result.add("afterBarrier");
                }
        );

        second.start();
        first.start();
        first.join();


        assertThat(result, is(expected));
    }

}