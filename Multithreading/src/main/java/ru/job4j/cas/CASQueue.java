package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASQueue<T> {

    private final AtomicReference<Node<T>> head = new AtomicReference<>();

    private final AtomicReference<Node<T>> tail = new AtomicReference<>();

    public void push(T value) {
        Node<T> temp = new Node<>(value);
        Node<T> refTail;
        Node<T> refHead;
        do {
            refTail = tail.get();
            refHead = head.get();
            if (refTail == null) {
                head.set(temp);
                tail.set(temp);
                temp.next = null;
                return;
            }

            tail.get().next = temp;
            tail.set(temp);
            temp.next = null;
        } while (!tail.compareAndSet(refTail, temp) && !head.compareAndSet(refHead, refHead));
    }

    public T poll() {
        Node<T> temp;
        Node<T> refHead;
        Node<T> refTail;
        do {
            refHead = head.get();
            refTail = tail.get();
            if (refHead == null) {
                throw new IllegalStateException("Queue is empty");
            }
            temp = refHead.next;
        } while (!head.compareAndSet(refHead, temp) && !tail.compareAndSet(refTail, refTail));
        return refHead.value;
    }

    private static final class Node<T> {
        final T value;

        Node<T> next;

        public Node(final T value) {
            this.value = value;
        }
    }
}
