package ru.job4j.cas;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CASStackTest {

    @Test
    public void when3PushAndPollByTwoThreadsThen4321() throws InterruptedException {
        CASStack<Integer> stack = new CASStack<>();
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
        List<Integer> expected = new ArrayList<>(Arrays.asList(4, 3, 2, 1));
        assertThat(result, is(expected));
    }

    @Test
    public void when1PushThen1Poll() {
        CASStack<Integer> stack = new CASStack<>();
        stack.push(1);
        assertThat(stack.poll(), is(1));
    }

    @Test
    public void when2PushThen2Poll() {
        CASStack<Integer> stack = new CASStack<>();
        stack.push(1);
        stack.push(2);
        assertThat(stack.poll(), is(2));
        assertThat(stack.poll(), is(1));
    }

}