package ru.job4j.cas;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CASQueueTest {

    @Test
    public void when1PushThen1Poll() {
        CASQueue<Integer> queue = new CASQueue<>();
        queue.push(1);
        assertThat(queue.poll(), is(1));
    }

    @Test
    public void when2PushThen2Poll() {
        CASQueue<Integer> queue = new CASQueue<>();
        queue.push(1);
        queue.push(2);
        assertThat(queue.poll(), is(1));
        assertThat(queue.poll(), is(2));
    }

    @Test
    public void when3PushAndPollByTwoThreadsThen1234() throws InterruptedException {
        CASQueue<Integer> stack = new CASQueue<>();
        List<Integer> result = new ArrayList<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        Thread first = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                result.add(stack.poll());
            }
        });
        Thread second = new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                result.add(stack.poll());
            }
        });
        first.start();
        first.join();
        second.start();
        second.join();
        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        assertThat(result, is(expected));
    }
}