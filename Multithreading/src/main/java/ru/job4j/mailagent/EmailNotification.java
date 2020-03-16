package ru.job4j.mailagent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        String userName = user.getUserName();
        String email = user.getEmail();
        String subject = "subject = Notification " + userName + "to Email" + email;
        String body = "body = Add a new event to " + user.getUserName();
        pool.execute(
                () -> send(subject, body, email));
    }

    public void send(String subject, String body, String email) {

    }

    public void close() {
        this.pool.shutdown();
    }
}
